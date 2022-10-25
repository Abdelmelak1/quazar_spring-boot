<template>
    <MainLayout/>
    <q-btn to="/login" color="green" text-color="black" label="Login" />
    <div class="q-pa-md flex flex-center">
      <div class="q-gutter-y-md column" style="max-width: 300px">
        <q-form
          @submit="handleSubmit(onSubmit)"
          @reset="onReset"
          class="q-gutter-md"
        >

<q-input filled v-model="username" type="text" label="Username" outlined>
            <template v-slot:prepend>
              <q-icon name="face" color="orange" />
            </template>
          </q-input>
        <q-input filled v-model="lastName" type="text" label="First Name" outlined>
            <template v-slot:prepend>
                <q-icon name="person" color="orange" />
            </template>
          </q-input>
        <q-input filled v-model="firstName" type="text" label="Last Name" outlined>
            <template v-slot:prepend>
              <q-icon name="person" color="orange" />
            </template>
          </q-input>
  
          
          
  
          <q-input
            filled
            v-model="email"
            type="email"
            label="Email"
            outlined
          >
            <template v-slot:prepend>
              <q-icon name="email" color="orange" />
            </template>
          </q-input>
  
          <!-- <q-input
       bg-color="white"
      -->
         
      
  <!---------------------------------------------------------------------------------->
  <q-input
          v-if="showPassword"
            filled
            v-model="password"
            
            type="text"
            label="Mot de passe"
            hint="Enter a strong password"
          >
            <template v-slot:prepend>
              <q-icon name="lock" color="orange" />
            </template>
            
            <template  v-slot:append>
              <q-icon @click="this.showPassword = !this.showPassword" name="visibility" color="orange" />
            </template>
          
          </q-input>
      <!---------------------------------------------------------------------------------->
          
  
          <q-input
          v-if="!showPassword"
            filled
            v-model="password"
            
            type="password"
            label="Password"
            hint="Enter a strong password"
          >
            <template v-slot:prepend>
              <q-icon name="lock" color="orange" />
            </template>
            
            <template  v-slot:append>
              <q-icon @click="this.showPassword = !this.showPassword" name="visibility" color="orange" />
            </template>:
          
          </q-input>
  
          <!---------------------------------------------------------------------------------->
  
          <div>
            <q-btn label="SIGN UP" type="submit" color="primary" />
            <q-btn
              label="Reset"
              type="reset"
              color="primary"
              flat
              class="q-ml-sm"
            />
  
            <!-- 
            <q-btn flat label="Cancel" v-close-popup color="primary"  class="q-ml-sm"></q-btn>
            <q-btn flat @click="add" label="Add"  v-close-popup color="primary"></q-btn>
            -->
          </div>
        </q-form>
      </div>
    </div>
  </template>


<script>
    import { api } from "src/boot/axios";

    
    
 
    export default {
        data() {
            return {

                firstName: "",
                lastName: "",
                email: "",
                password: "",
                username:"",
            };
        },
        methods: {
          async  handleSubmit() {
                // eslint-disable-next-line
                console.log(this.username);
                try{
               const resp = await api.post("http://localhost:8081/user/register", {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    email: this.email,
                    password: this.password,
                    username: this.username
                });}catch(error){

  console.error(error);
                }
           
                    //.then((resp) => {
                    console.log("adding customer");
                    console.log()
                    //console.log(resp.headers["jwt-token"]);
                    //localStorage.setItem('token', resp.headers["jwt-token"]);
               
    
                  window.issignedIn = true ;
                 //Bus.$emit ( "loggedIn " ) ; // emitting event "Logged In"
                  this.$router.push ( '/' ) ;
    
                    
                   
                //});
                this.username = "";
                this.firstName = "";
                this.lastName = "";
                this.email = "";
                this.password = "";
                alert("Done");
                //this.$router.push('/add')
            },
            hello() {
                console.log("visibility button");
            },
            onReset() {
                this.username = "";
                this.firstName = "";
                this.lastName = "";
                this.email = "";
                this.password = "";
                requestAnimationFrame(() => {
                    this.$refs.observer.reset();
                });
            },
        }

    };
    </script>