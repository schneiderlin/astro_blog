import { defineCollection, z } from 'astro:content';

const blogCollection = defineCollection({
  type: 'content',
  schema: z.object({
    title: z.string(),
    author: z.string().optional(),
    categories: z.array(z.string()).optional(),
    date: z.string(),
    description: z.string(),
    lang: z.string(),
  }),
});

export const collections = {
  'blog': blogCollection,
};
