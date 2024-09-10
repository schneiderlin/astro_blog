import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { useStore } from '@nanostores/react'
import { $authStore } from '@clerk/astro/client'
import { $userStore } from '@clerk/astro/client'
import { SignedIn, SignedOut, SignInButton, UserButton } from "@clerk/astro/react";
import { useSyncExternalStore, useTransition, useEffect, useState } from "react";

export default function Comment() {
        // const { userId, sessionId } = useStore($authStore)
        const user = useStore($userStore)
        // console.log("userId", userId)
        console.log("user", user)

        if (!user) {
                return (
                        <div className="w-full max-w-md mx-auto text-center">
                                <p className="mb-4">Please sign in to leave a comment.</p>
                                <SignedOut>
                                        <SignInButton />
                                </SignedOut>
                                <SignedIn>
                                        <UserButton />
                                </SignedIn>
                        </div>
                )
        }

        return (
                <form className="space-y-4 w-full max-w-md mx-auto">
                        <div>
                                <p className="block text-sm font-medium text-gray-700">
                                        {`Comment as ${user?.fullName}`}
                                </p>
                                <Textarea
                                        id="comment"
                                        required
                                        className="mt-1"
                                        placeholder="Your comment"
                                        rows={4}
                                />
                        </div>
                        <Button type="submit" className="w-full">Submit Comment</Button>
                </form>
        )
}




