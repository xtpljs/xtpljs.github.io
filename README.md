## xtpl boilerplate
```html
<!DOCTYPE html>
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

	<title>xtpl :: page</title>

	<meta name="keywords" content=""/>
	<meta name="description" content=""/>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
</head>
<body>
	<script x-ctrl="main" type="text/xtpl">
		h1 | Hello {{ctx.name}}!
		h2 | This is xtpl boilerplate.
	</script>

	<script src="/js/xtpl.min.js"></script>
	<script>
		xtpl.ctrl("main", function (ctx){
			ctx.name = "world";
		});
	</script>
</body>
</html>
```


## Create element
```xtpl
// tag name
header { }

// class names (default tag name "div")
.btn.btn-success { }

// ID
#main { }

// tag name + ID
nav#main { }

// tag name + ID + class name
nav#portal.drak { }

// tag name + attributes
input[type="text"] { }

// OR
input { type: "text" }

// more
input[type="checkbox"][checked] { }

// and more
input.xlarge[type="checkbox"] { checked: true }
```


## Simple block
```xtpl
ul.nav {
	li > a[href="/"] | Home
	li > a[href="/about/"] | About
	li > a[href="/sitemap/"] | Sitemap
}
```
```xml
<ul class="nav">
	<li><a href="/">Home</a></li>
	<li><a href="/about/">About</a></li>
	<li><a href="/sitemap/">Sitemap</a></li>
</ul>
```


## "if else" statement
```xtpl
if ctx.name {
	h3 | Good day, {{ctx.name}}!

	.btn.btn-warning {
		x-tap: delete ctx.name;
		| Logout
	}
} else {
	.btn.btn-primary {
		x-tap: ctx.name = prompt("Name:");
		| Sing in
	}
}
```


## Each
```xtpl
ul.nav {
	for item in ctx.nav {
		li > a {
			href: item.href
			| {{item.text}}
		}
	}
}

hr {}

// or so
for (idx, text) in ctx.list {
	div | {{idx + 1}}. {{text}}
}
```


## Tag declaration
```xtpl
// Define "icon" element
&icon = span.glyphicon {
	class: "glyphicon-{{ctx.name}}"
}

// Define "button"
&btn = button.btn {
	// modifier
	class: "btn-{{ctx.mod || 'default'}}"

	// Text node
	| {{ctx.text}}

	if ctx.icon {
		&icon { x-name: ctx.icon }
	}
}

// Usage
.confirm {
	// Header with icon
	h3 {
		| How are you
		&icon { name: "question-sign" }
	}

	// Button without icon
	&btn { text: "Fine", mod: "success" }

	// Button with icon
	&btn { text: "Excellent", icon: "heart", mod: "primary" }
}
```



## Hello
```xtpl
.well {
   h4 | Hello {{ctx.name || "%username%"}}!
   input.form-control { type: "text", x-model: ctx.name }
}
```



## Counter
```xtpl
input.btn.btn-success.xlarge[type="button"] {
	x-tap: ctx.counter++;
	value: ctx.counter || "Click me"
}
```
```js
xtpl.ctrl('main', function (ctx){
	ctx.counter = 0;
});
```



## List
```xtpl
// Form add an item
form {
	x-submit: ctx.addItem(ctx.newItem);
	input.input-small.form-control[type="text"] {
		placeholder: "Add new item"
		x-model: ctx.newItem
	}
}

// Item list
ul.nav.nav-pills.nav-stacked {
	for item in ctx.items {
		li {
			x-tap: ctx.activeItem = item;
			class: { active: item === ctx.activeItem }
			a[href="#"] | {{item}}
		}
	}
}

// Sorting
div {
	button.btn.btn-small {
		x-tap: ctx.sortItems(ctx.SORT_ASC);
		| asc
	}

	| &nbsp; &nbsp;

	button.btn.btn-small {
		x-tap: ctx.sortItems(ctx.SORT_DESC);
		| desc
	}
}
```
```js
xtpl.ctrl('list', function (ctx){
	// New item model
	ctx.newItem = '';

	// Default items
	ctx.items = ['Alpha', 'Beta', 'Gamma'];

	// Active item index in items
	ctx.activeItem = null;

	// Add to items
	ctx.addItem = function (){
		if( ctx.newItem.length > 0 ){
			ctx.items.push(ctx.newItem);
			ctx.newItem = '';
		}
	};

	// @const
	ctx.SORT_ASC = function (a, b){
		return	a == b ? 0 : (a > b ? 1 : -1);
	};

	// @const
	ctx.SORT_DESC = function (a, b){
		return	a == b ? 0 : (a > b ? -1 : 1);
	};

	// Sort items by type
	ctx.sortItems = function (fn){
		ctx.items.sort(fn);
	};
});
```




## Todos
```xtpl
h4 | Todos
div {
	span | {{ctx.remaining()}} of {{ctx.todos.length}} remaining [&nbsp;

	a[href=""] {
		x-tap: ctx.archive()
		| archive
	}
	| &nbsp;] |

	ul.list-unstyled {
		for todo in ctx.todos {
			li {
				input[type="checkbox"] { x-model: todo.done }
				span[class="done-{{todo.done}}"] | {{todo.text}}
			}
		}
	}

	form {
		x-submit: ctx.addTodo()
		.input-group {
			input.form-control.input-small[type="text"] {
				x-model: ctx.todoText
				placeholder: "add new todo here"
			}
			.input-group-btn > input.btn.btn-primary.btn-small {
				type: "submit"
				value: "add"
			}
		}
	}
}
```
```js
xtpl.ctrl('todos', function (ctx){
	ctx.todos = [
		{text: 'visit xtpl.ru', done: true},
		{text: 'learn xtpl', done: false},
		{text: 'have fun', done: false}
	];

	ctx.addTodo = function (){
		ctx.todos.push({text: ctx.todoText, done: false});
		ctx.todoText = '';
	};

	ctx.remaining = function (){
		var count = 0;
		xtpl.each(ctx.todos, function (todo){
			count += todo.done ? 0 : 1;
		});
		return count;
	};

	ctx.archive = function (){
		var oldTodos = ctx.todos;
		ctx.todos = [];
		xtpl.each(oldTodos, function (todo){
			if( !todo.done ) ctx.todos.push(todo);
		});
	};

	return	ctx;
});
```
