import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { useStore } from '@nanostores/react'
import { $userStore } from '@clerk/astro/client'
import { useAuth } from "@clerk/astro/react";
import { useEffect, useState } from "react";
import { auth, getComments, postComment } from "@/firebase";
import { signInWithCustomToken } from "firebase/auth";
import { Timestamp } from "firebase/firestore";

interface Comment {
  id: string;
  author: string;
  content: string;
  createdAt: Timestamp;
}

interface CommentProps {
  articleId: string;
}

export default function Comment({ articleId }: CommentProps) {
	const user = useStore($userStore)
	const { getToken, userId } = useAuth()
	const [comments, setComments] = useState<Comment[]>([])
	const [newComment, setNewComment] = useState("")

	useEffect(() => {
		const signIntoFirebaseWithClerk = async () => {
			if (userId) {
				try {
					const token = await getToken({ template: 'integration_firebase' })
					const userCredentials = await signInWithCustomToken(auth, token || '')
					console.log('User signed in to Firebase:', userCredentials.user)
				} catch (error) {
					console.error('Error signing in to Firebase:', error)
				}
			}
		}

		signIntoFirebaseWithClerk()
	}, [userId, getToken])

	// Fetch comments
	const fetchComments = async () => {
		try {
			const commentsData = await getComments(articleId) as Comment[];
			setComments(commentsData);
		} catch (error) {
			console.error('Error fetching comments:', error);
		}
	};

	useEffect(() => {
		fetchComments();
	}, []);

	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		if (!newComment.trim() || !user) return;

		try {
			await postComment(articleId, user.username || "Anonymous", newComment);
			setNewComment("");
			await fetchComments();
		} catch (error) {
			console.error('Error posting comment:', error);
		}
	};

	return (
		<div className="flex flex-col space-y-6 p-4">
			<h2 className="text-2xl font-bold">Comments</h2>
			
			{/* Comments list */}
			<ul className="space-y-4">
				{comments.map((comment) => (
					<li key={comment.id} className="bg-white shadow rounded-lg p-4">
						<div className="flex justify-between items-start">
							<span className="font-semibold text-gray-800">{comment.author}</span>
							<span className="text-sm text-gray-500">
								{new Date(comment.createdAt.seconds * 1000).toLocaleString()}
							</span>
						</div>
						<p className="mt-2 text-gray-600">{comment.content}</p>
					</li>
				))}
			</ul>

			{/* New comment form - only show if user is logged in */}
			{userId ? (
				<form className="mt-6" onSubmit={handleSubmit}>
					<Textarea
						placeholder="Write a comment..."
						className="w-full p-2 border rounded-md"
						value={newComment}
						onChange={(e) => setNewComment(e.target.value)}
					/>
					<Button type="submit" className="mt-2">
						Post Comment
					</Button>
				</form>
			) : (
				<p className="mt-6 text-center text-gray-600">
					Please sign in to post a comment.
				</p>
			)}
		</div>
	)
}




