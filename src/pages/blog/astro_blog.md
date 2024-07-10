---
layout: '@layouts/BlogPostLayout.astro'
title: astro blog
author: linzihao
date: "2024-07-10"
---

一个命令
`pnpm astro add mdx`
就可以在 astro 项目里面加上 mdx 支持了, 会自动加依赖, 并且修改 astro 配置文件, 把插件加上.

`pnpm astro add react`
加上 react, 会自动添加依赖, 修改 tsconfig, 修改 astro 配置

`pnpm astro add tailwind`

其他 UI 相关的依赖
`pnpm i @headlessui/react`
`pnpm i @heroicons/react@v1`


`pnpm install -D @tailwindcss/typography`
然后在 tailwind.config.js 里面修改
```javascript
plugins: [
    require('@tailwindcss/typography'),
    // ...
],
```
现在多了一个 utility class prose 可以用了