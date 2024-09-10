import { initializeApp } from "firebase/app";
import { doc, getDoc, getFirestore, collection, getDocs, addDoc, serverTimestamp } from "firebase/firestore";
import { useAuth } from '@clerk/astro/react'
import { getAnalytics } from "firebase/analytics";
import { getAuth, signInWithCustomToken } from 'firebase/auth'

const firebaseConfig = {
  apiKey: "AIzaSyD2Ext-4Dr7TRiRcP1N2_wP3XVOzU8Ly_g",
  authDomain: "linzihao-blog.firebaseapp.com",
  databaseURL: "https://linzihao-blog-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "linzihao-blog",
  storageBucket: "linzihao-blog.appspot.com",
  messagingSenderId: "843608830662",
  appId: "1:843608830662:web:a73f26fc4b78f31c0addc7",
  measurementId: "G-8D2S1E2KT2"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
export const db = getFirestore(app);
export const auth = getAuth(app)

export const getComments = async (articleId: string) => {
  const commentsRef = collection(db, 'blog', articleId, 'comments');
  const commentsSnapshot = await getDocs(commentsRef);
  
  if (!commentsSnapshot.empty) {
    const comments = commentsSnapshot.docs.map(doc => ({
      id: doc.id,
      ...doc.data()
    }));
    console.log('Comments for article:', comments);
    return comments;
  } else {
    console.log('No comments found for this article');
    return [];
  }
}

export const postComment = async (articleId: string, author: string, content: string) => {
  try {
    const commentsRef = collection(db, 'blog', articleId, 'comments');
    const newComment = await addDoc(commentsRef, {
      author,
      content,
      createdAt: serverTimestamp()
    });
    console.log('Comment added with ID:', newComment.id);
    return newComment.id;
  } catch (error) {
    console.error('Error adding comment:', error);
    throw error;
  }
}