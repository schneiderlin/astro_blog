---
layout: '@layouts/BlogPostLayout.astro'
title: vosquet
---

how bosquet work

to understand bosquet, first need to understand pathom.

## basic usage
there are 3 basic usuage of bosquet `generate` function
1. generate completion
```clojure
(generate "The capital of France is: ")
```

2. generate Chat
```clojure
(generate [[:system "You are a helpful assistant"]
           [:user "hi"]])
```

3. generate graph
input is a graph, can have many node being LLM node, can be dependencies between nodes.
there are 3 types of node, string template, LLM or function call.
bosquet can analyze the graph, resolve each node at topological order.

this is useful for getting context, data, example or user input from outside. and glue many LLM together, some LLM just focus on some specific sub task.

```clojure
(generate
 {:question-answer "Question: {{question}}  Answer: {{answer}}"
  :answer          (llm :gpt-3.5-turbo k/model-params {:temperature 0.8 :max-tokens 120})
  :self-eval       ["{{question-answer}}"
                    ""
                    "Is this a correct answer? {{test}}"]
  :test            (llm :mistral-small k/model-params {:temperature 0.2 :max-tokens 120})}
 {:question "What is the distance from Moon to Io?"})
```
