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

const zettelCollection = defineCollection({
  type: 'content',
  schema: z.object({
    title: z.string(),
    tags: z.array(z.string()),
  })
});

export const collections = {
  'blog': blogCollection,
  'zettel': zettelCollection,
};
