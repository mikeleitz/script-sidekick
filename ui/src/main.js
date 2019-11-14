import Vue from 'vue'
import { VBScrollspyPlugin, BootstrapVue, DropdownPlugin, TablePlugin, LayoutPlugin, ModalPlugin, CardPlugin } from 'bootstrap-vue'
import App from './App.vue'
import router from './router'

import ToggleButton from 'vue-js-toggle-button'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false
Vue.use(BootstrapVue)

// This imports all the layout components such as <b-container>, <b-row>, <b-col>:
Vue.use(LayoutPlugin)

// This imports <b-modal> as well as the v-b-modal directive as a plugin:
Vue.use(ModalPlugin)

// This imports <b-card> along with all the <b-card-*> sub-components as a plugin:
Vue.use(CardPlugin)

// This imports directive v-b-scrollspy as a plugin:
Vue.use(VBScrollspyPlugin)

// This imports the dropdown and table plugins
Vue.use(DropdownPlugin)
Vue.use(TablePlugin)

Vue.use(ToggleButton)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

router.push({ path: '/' })
