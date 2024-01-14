# Example repo for clj-kondo hook to detect derived Vars

This is an example repository that demostrates how a clj-kondo hook can detect [derived Vars](https://clojure.org/guides/repl/enhancing_your_repl_workflow#writing-repl-friendly-programs)

The source code for the hook can be found from [./.clj-kondo/hooks/def.clj](./.clj-kondo/hooks/def.clj)

See the blog post here: [https://www.mikkokoski.com/blog/derived-vars/index.html](https://www.mikkokoski.com/blog/derived-vars/index.html)

Clone the project and run clj-kondo:

``` bash
clj -M:clj-kondo --lint src
```

Because the hook relies on cached analysis data, the first time you run it you will not see warnings:

``` bash
➜  clj -M:clj-kondo --lint src
linting took 80ms, errors: 0, warnings: 0
```

Run it again, now with a populated cache, and you will see two warnings:

``` bash
➜  clj -M:clj-kondo --lint src
src/core.clj:17:8: warning: fn-sym-in-def: welcome-page-handler - use var quoting
src/core_cljs.cljs:17:8: warning: fn-sym-in-def: welcome-page-handler - use function wrapping
linting took 87ms, errors: 0, warnings: 2
```

If you want to try again without populated cache, you can delete the cache directory:

``` bash
rm -rf .clj-kondo/.cache
```
