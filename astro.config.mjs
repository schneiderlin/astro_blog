import { defineConfig } from 'astro/config';
import mdx from "@astrojs/mdx";
import react from "@astrojs/react";
import tailwind from "@astrojs/tailwind";
import partytown from "@astrojs/partytown";
import vercel from "@astrojs/vercel/serverless";
import sitemap from "@astrojs/sitemap";
import icon from "astro-icon";
import clerk from "@clerk/astro";


// https://astro.build/config
export default defineConfig({
  site: "https://linzihao.com",
  integrations: [mdx(), react(), tailwind({
    applyBaseStyles: false
  }), partytown(), sitemap(), icon(), clerk()],
  output: "server",
  adapter: vercel()
});
