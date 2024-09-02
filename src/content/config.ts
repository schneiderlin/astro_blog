import { defineCollection, z } from 'astro:content';

const blogCollection = defineCollection({
  type: 'content',
  schema: z.object({
    title: z.string(),
    author: z.string().optional(),
    tags: z.array(z.string()),
    date: z.string(),
    description: z.string(),
    ogImage: z.string().optional(),
  })
});

export const collections = {
  'blog': blogCollection,
};
