<template >
    <div>
        <h1>Client List</h1>
        <table>
            <tr>
                <td>Nom</td>
                <td>Prenom</td>
                <td>Email</td>
                <td>Delete</td>
            </tr>
                
            <tr v-for="item in list" v-bind:key="item.idClient">
                <td>{{item.nom}}</td>
                <td>{{item.prenom}}</td>
                <td>{{item.email}}</td>
                
            </tr>
        </table>

        
    </div>
</template>
<script>
  
import { api } from 'src/boot/axios';



 /*   
  import { ref, Ref } from 'vue';
    import { api } from 'src/boot/axios';
    import { useQuasar } from 'quasar';


        setup(){
        const $q = useQuasar()
        const data = ref(null)

        function loadData(){
            api.getet('/retrieve-all-clients')
        .then((response) => {
            data.value = response.data   }) 
        
        } 
            return {data, loadData}
        }
*/

    export default {
        name:"ClientList",
        data(){
            return {list:undefined,
                
            }
        },
        
         async mounted(){
           
            /*
            api.post('http://localhost:9080/SpringRest/clients/add-client', {
    
    "nom": "Kaltoum1",
    "prenom":"Abdelmelak1",
    "email": "abdelmelak1.kaltoum@gmail.com",
    "password": 1234567,
    "dateNaissance": "1997-10-31",
    "factures": []
})
            .then((resp) =>{
                console.log("adding client")
                console.log(resp)
            })
            */
            const resp = await api({
        method: 'GET',
        url: 'http://localhost:8081/user/list',
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      })
      this.list = resp.data
      console.log("clients list")
      console.log(this.list)
       
        }
        
    }
    
</script>
