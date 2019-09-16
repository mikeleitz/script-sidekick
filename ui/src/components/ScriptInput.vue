<!--
  -  Copyright (c) 2019, Michael Leitz
  -  <p/>
  -  Licensed under the Apache License, Version 2.0 (the "License");
  -  you may not use this file except in compliance with the License.
  -  You may obtain a copy of the License at
  -  <p/>
  -  http://www.apache.org/licenses/LICENSE-2.0
  -  <p/>
  -  Unless required by applicable law or agreed to in writing, software
  -  distributed under the License is distributed on an "AS IS" BASIS,
  -  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  -  See the License for the specific language governing permissions and
  -  limitations under the License.
  -->

<template>
  <div>
    <div class="form-group">
      <label for="scriptInputNumbers">Numbers</label>
      <input id="scriptInputNumbers" type="text" class="form-control" placeholder="Numbers from store" v-model="storeState.name">
    </div>

<!--
    <div class="form-group">
      <label for="scriptInputLongName">Long name</label>
      <input id="scriptInputLongName" type="text" class="form-control" placeholder="Long name" v-model="longName">
    </div>

    <div class="form-group">
      <label for="scriptInputShortName">Short name</label>
      <input id="scriptInputShortName" type="text" class="form-control" placeholder="Short name" v-model="shortName"
             maxlength="1">
    </div>
    <div class="form-group">
      <label for="inputRequired">Decree</label>

      <div id="inputRequired" class="input-group mb-3">
        <toggle-button :width="110"
                       :height="35"
                       :margin="10"
                       :font-size="14"
                       :labels="{checked: 'Required', unchecked: 'Optional'}"
                       v-model="decree"/>

      </div>
    </div>

    <div class="form-group">
      <label for="scriptInputHelpText">Help text</label>
      <input id="scriptInputHelpText" type="text" class="form-control" placeholder="Help text" v-model="helpText">
    </div>

    <div class="form-group">
      &lt;!&ndash; <button @click="()=>del(record.id)">&times;</button> &ndash;&gt;
      <button class="btn btn-outline-secondary btn-sm" @click="()=>removeInput(1)">Remove this input</button>
    </div>

    <div>
      <hr>
    </div>

    <div class="form-group">
      <button class="btn btn-outline-secondary btn-sm" @click="addInput">Add another input</button>
    </div>
-->

  </div>
</template>

<script>
    import { store } from "../store.js";

    export default {
        name: "ScriptInput",
        props: {
            id: {
                required: false,
                type: Number,
                default: -1,
            },
            longName: {
                required: false,
                type: String,
                default: '',
            },
            shortName: {
                required: false,
                type: String,
                default: '',
            },
            decree: {
                required: false,
                type: Boolean,
                default: false,
            },
            helpText: {
                required: false,
                type: String,
                default: '',
            }
        },
        data() {
            return {
                storeState: store.state
            }
        },
        watch: {
            inputVal(val) {
                this.$emit('input', val);
            }
        },
        methods: {
            addInput: function (event) {
                alert('Long name  :' + this.longName)
                alert('Short name :' + this.shortName)
                alert('Decree     :' + this.decree)
                alert('Help text  :' + this.helpText)
            },
            removeInput: function (id) {
                alert('Deleting id ' + this.id)
            }
        }
    }
</script>

<style scoped>

</style>
