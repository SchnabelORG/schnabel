<template>
	<div id="navapp">
		<nav id="navbar">
			<div id="nav" :class="{sticky:active}">
				<div class="nav-container" :class="toggleNavClass()">
					<!-- NOTE(Jovan): Logo -->
					<div class="navbar-item logo">
						schnabel
					</div>
					<div id="nav-links">
						<slot></slot>
						<router-link v-if="jws" to="/logout">Logout</router-link>
					</div>
				</div>
			</div>
		</nav>
	</div>
</template>
<script>
	module.exports =
	{
		data: function()
		{
			return{
				active: false,
			}

		},
		methods:
		{
			toggleNavClass()
			{
				if(this.active == false)
				{
					return "navbar";
				}
					else
				{
					return "navbar-sticky";
				}
			},
		},
		computed:
		{
			jws: function() {
				return localStorage.jws;
			}
		},
        mounted()
        {
            // window.document.onscroll = () =>
            // {
            //     let navapp = document.getElementById("navapp");
            //     if(!navapp)
            //     {
            //         return;
            //     }
            //     if(window.scrollY > navapp.offsetTop)
            //     {
            //         this.active = true;
            //     }
            //     else
            //     {
            //         this.active = false;
            //     }
            // };
        },
	}
</script>
<style scoped>
	#navapp {
		--h: 8vh;
		background-color: #fff;
	}

	.navbar {
		transition: 100ms;
		padding: 0px 25px 0px 25px;
	}

	.navbar-sticky {
		transition: 100ms;
		padding: 0px 25px 0px 25px;
	}

	#nav {
		transition: 150ms;
		height: var(--h);
		width: 100%;
		/*background-color: #fff;*/
		/* position: fixed; */
		top: 0;
		z-index: 3;
	}

	#nav.sticky {
		transition: 150ms;
		background-color: #fff;
		box-shadow: 0px 1px 10px #999;
	}

	.nav-container {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		align-items: center;
		justify-content: space-between;
	}

	.sticky > .nav-container {
		flex-wrap: nowrap;
	}

	.nav-container > * {
		min-height: var(--h);
		flex-basis: 50%;
	}


	.navbar-dropdown:hover .navbar-list {
		display: block;
		top: 7vh;
	}

	#nav-links {
		display: flex;
		flex-direction: row;
		align-content: center;
		justify-content: flex-end;
	}

	#nav-links a {
		display: grid;
		place-items: center;
		color: #311403;
		text-decoration: none;
		text-align: center;
		font-size: 1rem;
		font-weight: 400;
		padding: 5px;
	}

	#nav-links a:hover {
		color: #e12454;
	}

	.navbar-item {
		margin: 0;
		padding: 0;
	}

	.logo {
		flex-grow: 1;
		display: flex;
		align-items: center;
	}

	.navbar-sticky > .logo {
		max-width: 13rem;
	}

	.nav-container {
		max-width: 75vw;
		margin: auto;
	}

	#nav > .nav-container > .logo {
		font-family: "Poppins";
		font-size: 1.5rem;
		border: none;
		color: #311403;
		text-transform: uppercase;
		font-weight: 600;
	}

	.navbar-list hr {
		padding: 0px;
		margin: 0px;
	}

	#nav.sticky > .nav-container > .logo{
		font-size: 1.8rem;
		border: none;
		color: #e12454;
		text-transform: uppercase;
		font-weight: 600;
	}
</style>