import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { useStore } from '@nanostores/react'
import { $userStore } from '@clerk/astro/client'
import { SignedIn, SignedOut, SignInButton, useAuth, UserButton } from "@clerk/astro/react";
import { collection, getDocs } from "firebase/firestore";
import { useEffect } from "react";
import { auth, db, getFirestoreData } from "@/firebase";
import { signInWithCustomToken } from "firebase/auth";

export default function Comment() {
        const user = useStore($userStore)
        const { getToken, userId } = useAuth()

        useEffect(() => {
                console.log("use effect")
                const f = async () => {
                        const querySnapshot = await getDocs(collection(db, "users"));
                        querySnapshot.forEach((doc) => {
                                console.log(`${doc.id} => ${doc.data()}`);
                        });
                }
                f()
                        .catch((error) => {
                                console.error("Error fetching users:", error);
                        });
        }, [])

        // Handle if the user is not signed in
        // You could display content, or redirect them to a sign-in page
        if (!userId) {
                return <p>You need to sign in with Clerk to access this page.</p>
        }

        const signIntoFirebaseWithClerk = async () => {
                const token = await getToken({ template: 'integration_firebase' })

                const userCredentials = await signInWithCustomToken(auth, token || '')
                // The userCredentials.user object can call the methods of
                // the Firebase platform as an authenticated user.
                console.log('User:', userCredentials.user)
        }

        return (
                <main style={{ display: 'flex', flexDirection: 'column', rowGap: '1rem' }}>
                        <button onClick={signIntoFirebaseWithClerk}>Sign in</button>

                        {/* Remove this button if you do not have Firestore set up */}
                        <button onClick={getFirestoreData}>Get document</button>
                </main>
        )
}




