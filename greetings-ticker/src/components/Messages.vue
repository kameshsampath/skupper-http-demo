<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <table id="cloud-messages" class="tbl">
      <thead>
        <tr>
            <th>Cloud</th>
            <th>Message</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="msg in this.$root.messages" v-bind:key="msg.id">
          <td>{{ msg.cloud }}</td>
          <td>{{ msg.translatedText }}</td>
        </tr>
      </tbody>
      </table>
  </div>
</template>

<script>
export default {
  name: 'Messages',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
.tbl {
  width: 100%;  
  padding: 0;
}
thead>tr>th {
  font-weight: bold;
  font-size: large;
  background: #CCC;
}
tr>td:first-child {
  color: #42b983;
  font-weight: bold;
}
tr:nth-child(even) {background: #CCC}
tr:nth-child(odd) {background: #FFF}
</style>
