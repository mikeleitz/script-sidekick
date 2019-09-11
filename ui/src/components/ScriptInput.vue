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
        <toggle-button :value="true"
                       :width="110"
                       :height="35"
                       :margin="10"
                       :font-size="14"
                       :labels="{checked: 'Required', unchecked: 'Optional'}"
                       v-modal="decree"/>

      </div>
    </div>

    <div class="form-group">
      <label for="scriptInputHelpText">Help text</label>
      <input id="scriptInputHelpText" type="text" class="form-control" placeholder="Help text" v-model="helpText">
    </div>

    <div class="form-group">
      <!-- <button @click="()=>del(record.id)">&times;</button> -->
      <button class="btn btn-outline-secondary btn-sm" @click="()=>removeInput(1)">Remove this input</button>
    </div>

    <div>
      <hr>
    </div>

    <div class="form-group">
      <button class="btn btn-outline-secondary btn-sm" @click="addInput">Add another input</button>
    </div>

  </div>
</template>

<script>
    export default {
        name: "ScriptInput",
        props: {
            id: Number,
            longName: String,
            shortName: String,
            decree: Boolean,
            helpText: String
        },
        data: {},
        methods: {
            addInput: function (event) {
                alert('Long name  :' + this.$props.longName)
                alert('Short name :' + this.$props.shortName)
                alert('Decree     :' + this.$props.decree)
                alert('Help text  :' + this.$props.helpText)
            },
            removeInput: function (id) {
                alert('Deleting id ' + this.$props.id)
            }
        }
    }
</script>

<style scoped>

</style>
