(function (xtpl, $){
	"use strict";


	xtpl.mod('xcode', function (code){
		if( !code ){
			console.log(code);
		}

		return xtpl.utils.map(code.split('\n'), function (line, i){
			return line
				.replace(/(\|[^$]+)/, function (_, val){
					return val.replace(/&/g, '&amp;').replace(/"/g, '&quot;');
				})
				.replace(/(".*?")/g, '<span class="code-str">$1</span>')
				.replace(/((?:^\s*| > | = ))(\w+)([\.\s#[])/g, '$1<span class="code-node">$2</span>$3')
				.replace(/ > (\w+)/g, ' > <span class="code-node">$1</span>')
				.replace(/(&\w+\s)/, '<span class="code-decl">$1</span>')
				.replace(/(#\w+)/, '<span class="code-id">$1</span>')
				.replace(/((?:^\s*|>)\.)([.\w-]+)/g, '$1<span class="code-class">$2</span>')
				.replace(/(\s)(x-\w+:)/g, '$1<span class="code-xprop">$2</span>')
				.replace(/(\s)(\w+:)/g, '$1<span class="code-prop">$2</span>')
				.replace(/\[(\w[^=\]]+)/g, '[<span class="code-prop">$1</span>')
				.replace(/\t/g, '&nbsp; &nbsp;')
				.replace(/\s{2}/g, '&nbsp; &nbsp;')
				.replace(/(\/\/[^$]+)/, '<span class="code-comment">$1</span>')
				.replace(/(>|\s)(if|else|for|in)(\s|<)/g, '$1<span class="code-keyword">$2</span>$3')
			;
		}).join('\n');
	});


	xtpl.mod('toXML', function (code){
		return xtpl.utils.map(code.split('\n'), function (line){
			return xtpl.xparse(line).toString()
		}).join('\n');
	});



	/** @namespace hljs -- highlight.js */
	if( window.hljs ){
		xtpl.decl('x-highlight', {
			init: function (el, code){
				var type = $.attr(el, 'data-type');

				el.className += ' language-' + (type == 'js' ? 'javascript' : type);
				el.innerHTML = '<pre><code></code></pre>';

				el.firstChild.firstChild.appendChild(document.createTextNode(code));

				hljs.highlightBlock(el);
			},
			update: function (el, code){
				this.init(el, code);
			}
		});
	}


	// Load readme
	var readmeJSON = (function (){
		var readme = {};
		xtpl.utils.each(xtpl.utils.readFile('./README.md?rev=2013-08-07').split('## '), function (block){
			block = block.trim();

			var pos = block.indexOf("\n");
			var name = block.substr(0, pos);
			var regexp = /```(\w+)([\s\S]*?)```/g, match;

			readme[name] = { name: name };

			while( match = regexp.exec(block) ){
				readme[name][match[1]] = match[2].trim();
			}
		});
		return	readme;
	})();


	// Eval code
	(new Function(readmeJSON['List'].js))();
	(new Function(readmeJSON['Todos'].js))();


	// :[
	readmeJSON['List'].js = readmeJSON['List'].js.replace('list', 'main');
	readmeJSON['Todos'].js = readmeJSON['Todos'].js.replace('todos', 'main');


	xtpl.ctrl('main', function (ctx){
		$(document).on('click', 'a', function (evt){
			var hash = evt.currentTarget.hash;
			if( /#!/.test(hash) ){
				evt.preventDefault();
				location.hash = hash;
				window.onhashchange();
			}
		});

		window.onhashchange = function (){
			var hash = location.hash.replace(/^[^!]+!?/, '') || '/';
			var anchor = hash.slice(1, -1).replace(/\//, '-') || 'index';
			var offset = $('a[name="'+anchor+'"]').offset();

			ctx.$set('page', hash);

			// Scroll to anchor
			if( offset ){
				$('html,body').stop(true).animate({ 'scrollTop': offset.top }, 'slow');
			}
		};


		ctx.counter = 0;
		ctx.readme = readmeJSON;


//		console.log(ctx.readme);

		ctx.each_nav = [{ href: "#!/", text: "Splash" }, { href: "#!/api/", text: "Docs" }];
		ctx.each_list = ["Alpha", "Beta", "Gamma"];


		// Animation
		window.onhashchange();
		ctx.$delay(function (){
			ctx.$set('showH1', true);

			xtpl.utils.each(['Hello', 'Counter', 'List', 'Todos'], function (name, i){
				ctx.$delay(function (){
					ctx.readme[name].show = true;
				}, 200 * (i + 1));
			});

			window.onhashchange();
		});

		$(window).scroll(xtpl.utils.throttle(function (){
			if( ctx.page != '/' ){
				if( $(window).scrollTop() < 500 ){
					location.hash = '';
					window.onhashchange();
				}
			}
		}, 50));
	});
})(xtpl, jQuery);
