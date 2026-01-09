pathom is similar to logic programming, you specify the relation between things, and provide some initial fact.
pathom can derive all other consequences base on the fact.

because pathom is very powerful, it has many use cases.

in this post, I will show the capabilities of pathom.

## chain reasoning 
if I have a function to get temperature from city name.
and also have a function to test if a given temperature is hot.
then I can tell if a city is hot, by composing these 2 functions.

in pathom, you don't need to define a third function that is the composition of the two functions.
just put these 2 functions in an `environment`, it is called resolver in pathom parlance, pathom can derive base on the inputs and outputs of each resolver.

<!-- ![](2024-09-19-10-24-29.png) -->

you can now tell the `environment`, I have a city name, is this city hot?
<!-- ![](2024-09-19-10-30-25.png) -->

not need to know the intermedia node, that is abstraction.
get many things for free.

## resolver as computation
email -> first last -> full. another chain

get something from database

## multiple inputs and outputs 
each resolver can have multiple inputs and outputs


which and what complect, client only say which, not what

just use clojure def, defn, if for all the manipulation. no need for special syntax

<!-- ![alt text](image.png) -->
GQL 是 top down, EQL 是 bottom up.
GQL 每个遇到的节点都要 resolve, EQL 只是 set 一下 context, 后面不一定用

<!-- ![alt text](image-1.png) -->
更加 loose, entity 之间是没有关联的, 是 resolver 使得他们关联

<!-- ![alt text](image-2.png) -->
GQL 无法表达这个, 只能在 customer 下面显示 customer/name
GQL like tree, EQL like graph. 
in implementation time, GQL is one way. EQL just specify all the node and edge, dynamic.
structure 不应该是 server 决定的, 是 client 决定的, 把能力放到 query time

GQL 的 query 只能从固定的几个 root type 开始.
EQL 可以从任何地方开始, 有些是不需要 input 的. 可以在任何地方 ask, 只要有对应的 inputs

two ways traverse

from data I have to data I want, using smart map as example, full name.

## UI predicates
client dont care how to calculate some status or flag.
also the structure of this node should be client to determin

## 预览区
should be implement on server side?
the context is in the client. 
just operate on attribute level, don't operate on box level, to many box and unbox.

## fixture
fixture for every attribute, not every box
