/*
 *  Copyright (c) 2020, Michael Leitz
 *  <p/>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p/>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p/>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import Vue from 'vue'
import { VBScrollspyPlugin, BootstrapVue, DropdownPlugin, TablePlugin, LayoutPlugin, ModalPlugin, CardPlugin, TabsPlugin } from 'bootstrap-vue'
import App from './App.vue'
import router from './router'

import ToggleButton from 'vue-js-toggle-button'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false
Vue.use(BootstrapVue)
Vue.use(TabsPlugin)

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
