import { defineConfig } from 'astro/config';
import mdx from "@astrojs/mdx";
import react from "@astrojs/react";
import tailwind from "@astrojs/tailwind";
import partytown from "@astrojs/partytown";
import vercel from "@astrojs/vercel/serverless";
import sitemap from "@astrojs/sitemap";

// https://astro.build/config
export default defineConfig({
  site: "https://linzihao.com",
  integrations: [mdx(), react(), tailwind(), partytown(), sitemap()],
  output: "server",
  adapter: vercel({
    webAnalytics: {
      enabled: true
    }
  })
});