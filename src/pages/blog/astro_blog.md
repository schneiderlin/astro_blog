---
layout: '@layouts/BlogPostLayout.astro'
title: 博客使用的技术栈
author: linzihao
date: "2024-07-10"
description: An overview of the technologies and tools used to create this blog, including Astro, MDX, React, Tailwind CSS, and more.
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

图片用了 astro 的 Image component 做自动优化, 因为用的是 pnpm, 不会自动安装额外的依赖, 需要手动装一下
`pnpm add sharp`

## google analytic
`pnpm astro add partytown`
需要用到 partytown, partytown 是用来懒加载第三方 script 的, 可以优化加载速度

去 google analytic 创建个媒体资源, 会拿到对应的 gtag id. 
替换到 GoogleAnalytic component 就可以
```astro
<!-- Google tag (gtag.js) -->
<script
  type="text/partytown"
  async
  src="https://www.googletagmanager.com/gtag/js?id=tag"></script>
<script type="text/partytown">
  window.dataLayer = window.dataLayer || [];
  function gtag() {
    dataLayer.push(arguments);
  }
  gtag("js", new Date());

  gtag("config", "tag");
</script>

```


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

## 阅读进度条
astro component 里面可以直接写 `<script>` 和 `<style>` tag, 因此一个 component 就可以实现进度条功能
```astro
---
---
<div id="progress-bar">

</div>
<script>
    function updateProgressBar() {
        const {scrollTop, scrollHeight} = document.documentElement
        const scrollPercent = `${(scrollTop / (scrollHeight - window.innerHeight)) * 100}%`

        document.querySelector('#progress-bar')!.style.setProperty('--progress', scrollPercent)
    }

    document.addEventListener('scroll', updateProgressBar)
</script>

<style>
    #progress-bar {
        --progress: 0;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        height: 6px;
        width: var(--progress);
        background-color: #502020;
    }
</style>
```

## sitemap
`pnpm astro add sitemap`
需要在 astro.config.js 里面写 site
```javascript
import { defineConfig } from 'astro/config';
import sitemap from '@astrojs/sitemap';

export default defineConfig({
  // ...
  site: 'https://stargazers.club',
  integrations: [sitemap()],
});
```

再 npx astro build, 会打印出在哪个路径输出了 sitemap xml, 这一步只是为了检查
在 layout 里面把 sitemap 加到 head 里面
```astro
<head>
  ...
  <link rel="sitemap" href="/sitemap-index.xml" />
</head>
```