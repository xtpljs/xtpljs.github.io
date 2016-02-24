(function (window) {
	'use strict';

	const KEY_ENTER = 13;
	const FILTER_ALL = 'all';
	const FILTER_ACTIVE = 'active';
	const FILTER_COMPLETED = 'completed';

	const template = window['app-template'].textContent;

	const ctx = xtpl.bind(window['app-canvas'], template, {
		FILTER_ALL,
		FILTER_ACTIVE,
		FILTER_COMPLETED,

		todos: [],
		filter: location.href.split('#/')[1] || FILTER_ALL,

		filteredTodos() {
			if (this.filter === FILTER_ALL) {
				return this.todos;
			}
		},

		hasCompleted() {
			return this.todos.some(todo => todo.completed);
		},

		activeTodos() {
			return this.filteredTodos().filter(todo => !todo.completed);
		},

		handleAddTodo(evt) {
			if (evt.keyCode === KEY_ENTER) {
				const title = this.newTodo.trim();

				if (title) {
					this.newTodo = '';
					this.todos.push({
						title,
						completed: false
					});
				}
			}
		},

		handleToggle(todo) {
			todo.completed = !todo.completed;
		},

		handleRemove(todo) {
			this.todos.splice(this.todos.indexOf(todo), 1);
		},

		handleClearCompleted() {
			this.todos = this.todos.filter(todo => !todo.completed);
		}
	});
})(window);
