---
title: factor table
tags: ["scala", "functional programming"]
lang: "zh"
---

factor table 是用来存储条件概率的数据结构

factor table φ
φ是一个 function, 从 variables 的 assignment 到一个 real number 的 mapping.
`φ(v1, v2, v3) → [0, 1]`, 如果不在 0,1 范围, 或者合大于 1, 可以 normalize, 问题不大.

## product
两个 factor table 可以乘起来
如果两个 factor table 之间没有公共的 variable, 说明这些 variable 之间是 independent 的, 因此他们的 joint probability $P(x, y) = P(x)P(y)$
直接把variable合起来, 然后 p 简单乘起来就可以了.

如果有公共 variable, 那么需要以公共 variable 为 pivot, 逐个 partition.
这样每个 partition 里面的 pivot 值都是相等的. 相当于 condition 了 pivot = 某个值.
在这个 subset 里面做 factor product.
最后把这些 subset 都汇总起来, 就得到了完整的 product.