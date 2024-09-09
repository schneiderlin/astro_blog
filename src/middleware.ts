import { clerkMiddleware, createRouteMatcher } from "@clerk/astro/server";

const isProtectedRoute = createRouteMatcher(['/draft(.*)'])

export const onRequest = clerkMiddleware((auth, context) => {
    const { redirectToSignIn, userId, has } = auth()

    // Restrict admin routes to users with specific permissions
  if (
    false && isProtectedRoute(context.request)
    // !has({ permission: 'org:sys_domains_manage' })
  ) {
    console.log("redirect to sign in")
    // Add logic to run if the user does not have the required permissions; for example, redirecting to the sign-in page
    return redirectToSignIn()
  }
});