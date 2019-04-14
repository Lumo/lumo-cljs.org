Lumo is a standalone ClojureScript environment that runs on Node.js and the V8 JavaScript engine. It starts up instantaneously and has out-of-the-box access to the entire Node.js ecosystem.

* [Install](https://github.com/anmonteiro/lumo/wiki/Install)
* [Run](https://github.com/anmonteiro/lumo/wiki/Run)
* [Compatible Libs](https://github.com/anmonteiro/lumo/wiki/Compatible-libraries)
* [Invoke Node](https://github.com/anmonteiro/lumo/wiki/Invoke-node)
* [Command Line Options](https://github.com/anmonteiro/lumo/wiki/Cli-options)

Read blogs to know Lumo:

* [The fastest Clojure REPL in the world](https://anmonteiro.com/2016/11/the-fastest-clojure-repl-in-the-world/)
* [Compiling ClojureScript Projects Without the JVM](https://anmonteiro.com/2017/02/compiling-clojurescript-projects-without-the-jvm/)
* [Lumo: Brightening the Horizons for Clojurescript'ing](http://benzaporzan.me/blog/2018/3/26/lumo_brightening_the_horizons_for_clojurescripting/)
* [Node.js to Clojure in 60 seconds](https://medium.com/front-end-hacking/node-js-to-clojure-in-60-seconds-a996e0969471)

### REPL

Simplest way to install lumo:

```bash
$ brew install lumo
# or
$ npm install -g lumo-cljs
```

After installed, you will get a CLI tool called `lumo`.

```bash
$ lumo
Lumo 1.10.1
ClojureScript 1.10.520
Node.js v11.13.0
 Docs: (doc function-name-here)
       (find-doc "part-of-name-here")
 Source: (source function-name-here)
 Exit: Control+D or :cljs/quit or exit

cljs.user=> (println (+ 1 2 3))
6
nil
cljs.user=>
```

### Node.js & npm modules

Accessing Node.js global variables with js interop:

```bash
cljs.user=> (.log js/console js/process.argv)
[ '/usr/local/Cellar/lumo/1.8.0/bin/lumo', 'nexe.js' ]
nil
```

Loading npm modules from `node_modules/` with `js/require`:

```bash
cljs.user=> (def escape-html (js/require "escape-html"))
#'cljs.user/escape-html
cljs.user=> (escape-html "<div />")
"&lt;div /&gt;"
```

Also possible to specify dependencies inside `ns` form:

```clojure
(ns app.main (:require ["fs" :as fs]))

(fs/readFileSync "package.json" "utf8")
```

### Interpreter

Interpret a source file. Lumo also supports the `--classpath` option.

```bash
$ lumo main.cljs
```

### CLI options

To view all supported CLI options:

```bash
$ lumo -h
```
