import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

const app = new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  created() {
    this.sourceMessages()
  },
  data: {
    messages: []
  },
  methods: {
    sourceMessages(){
      let apiUrl = process.env.VUE_APP_MESSAGES_URL;
      console.log("Using Messages API URL :", apiUrl)
      let es = new EventSource(apiUrl+'/greetings/stream',
        {
          withCredentials: true,
          retry: 15000
        }
      );
  
      es.addEventListener('message', event =>{
          console.log(event);
          let data = JSON.parse(event.data);
          this.messages.unshift(data);
      },false);

      //TODO handle closed
      es.addEventListener('error', event => {
        console.log(event);
        if (event.readyState == EventSource.CLOSED) {
          console.log('Event was closed');
          console.log(EventSource);
        }
      }, false);
    }
  }
})

export default app
