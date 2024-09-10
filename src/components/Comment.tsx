import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { useStore } from '@nanostores/react'
import { $userStore } from '@clerk/astro/client'
import { SignedIn, SignedOut, SignInButton, useAuth, UserButton } from "@clerk/astro/react";
import { collection, getDocs } from "firebase/firestore";
import { useEffect, useState } from "react";
import { auth, db, getFirestoreData } from "@/firebase";
import { signInWithCustomToken } from "firebase/auth";

interface Comment {
  id: string;
  author: string;
  content: string;
  createdAt: string;
}

export default function Comment() {
	const user = useStore($userStore)
	const { getToken, userId } = useAuth()
	const [comments, setComments] = useState<Comment[]>([])

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
			const commentsData = await getFirestoreData("information_source") as Comment[];
			setComments(commentsData);
		} catch (error) {
			console.error('Error fetching comments:', error);
		}
	};

	useEffect(() => {
		fetchComments();
	}, []);

	// Handle if the user is not signed in
	if (!userId) {
		return <p className="text-center text-gray-600">You need to sign in with Clerk to access this page.</p>
	}

	return (
		<div className="flex flex-col space-y-6 p-4">
			<h2 className="text-2xl font-bold">Comments</h2>
			
			{/* Comments list */}
			<ul className="space-y-4">
				{comments.map((comment) => (
					<li key={comment.id} className="bg-white shadow rounded-lg p-4">
						<div className="flex justify-between items-start">
							<span className="font-semibold text-gray-800">{comment.author}</span>
							<span className="text-sm text-gray-500">{new Date(comment.createdAt).toLocaleString()}</span>
						</div>
						<p className="mt-2 text-gray-600">{comment.content}</p>
					</li>
				))}
			</ul>

			{/* New comment form */}
			<form className="mt-6">
				<Textarea
					placeholder="Write a comment..."
					className="w-full p-2 border rounded-md"
				/>
				<Button type="submit" className="mt-2">
					Post Comment
				</Button>
			</form>
		</div>
	)
}




