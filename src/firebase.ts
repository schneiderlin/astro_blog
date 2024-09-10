import { initializeApp } from "firebase/app";
import { doc, getDoc, getFirestore } from "firebase/firestore";
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

export const getFirestoreData = async () => {
  const docRef = doc(db, 'comment', "ijT8vMQ6QV75BmHiSCdL")
  const docSnap = await getDoc(docRef)
  if (docSnap.exists()) {
    console.log('Document data:', docSnap.data())
  } else {
    // docSnap.data() will be undefined in this case
    console.log('No such document!')
  }
}