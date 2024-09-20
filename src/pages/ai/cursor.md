---
layout: '@layouts/BlogPostLayout.astro'
title: cursor
---

## understand a codebase
use the following prompt to let the LLM guide me to understand the codebase step by step.
the main idea is to reduce halucination, by instruct the LLM only answer base on local context.
AI can read code faster then me, but I need to double check. the knowledge I checked will be save in some file next to the source code, or write comment in the code.

```text
I need to understand this codebase, help me. don't try to get the answer in one go. provide step by step guidance. maybe let's start by exploring the folder structure?
```

```text
the return value of {{function}}, what are they? tell me what it is, if you don't know, tell me where should I look for further information
```

## write assistant
I just use it to find grammar error and spelling check. 
AI can't write meaningful article, even after I provide a outline.