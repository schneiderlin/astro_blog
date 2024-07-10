---
layout: '@layouts/BlogPostLayout.astro'
title: 博客使用的技术栈
author: linzihao
date: "2024-07-10"
---
用的是 astro

## mdx

一个命令
`pnpm astro add mdx`
就可以在 astro 项目里面加上 mdx 支持了, 会自动加依赖, 并且修改 astro 配置文件, 把插件加上.

## react
`pnpm astro add react`
加上 react, 会自动添加依赖, 修改 tsconfig, 修改 astro 配置

## tailwind and style
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

## google analytic

## 评论系统
评论用的是 [giscus](https://giscus.app/zh-CN)
在 github 仓库里面 enable discussion 功能, 把仓库设置成 public 的, 否则看不到评论.
在 giscus 页面里面填仓库地址, 设置一下, 就会拿到一串 script, 把这个做成 astro 的 component 再引用到 blog layout 里面
```astro
<section class="giscus mx-auto mt-10 w-full"></section>

<script src="https://giscus.app/client.js"
        data-repo="schneiderlin/astro_blog"
        data-repo-id="R_kgDOMUUSTw"
        data-category="Blog Post Comments"
        data-category-id="DIC_kwDOMUUST84Cgr-R"
        data-mapping="url"
        data-strict="0"
        data-reactions-enabled="1"
        data-emit-metadata="0"
        data-input-position="bottom"
        data-theme="preferred_color_scheme"
        data-lang="zh-CN"
        data-loading="lazy"
        crossorigin="anonymous"
        async>
</script>
```