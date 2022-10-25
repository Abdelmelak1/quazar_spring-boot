<template>
  <MainLayout />
  <q-btn to="/signup" color="pink" text-color="black" label="Sign Up" />
  <div class="q-pa-md flex flex-center">
    <div class="q-gutter-y-md column" style="max-width: 300px">
      <q-form
        @submit="handleSubmit(onSubmit)"
        @reset="onReset"
        class="q-gutter-md"
      >
        <q-input
          filled
          v-model="username"
          type="text"
          label="Username"
          outlined
        >
          <template v-slot:prepend>
            <q-icon name="person" color="orange" />
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
          label="Password"
          hint="Enter a strong password"
        >
          <template v-slot:prepend>
            <q-icon name="lock" color="orange" />
          </template>

          <template v-slot:append>
            <q-icon
              @click="this.showPassword = !this.showPassword"
              name="visibility"
              color="orange"
            />
          </template>
        </q-input>
        <!---------------------------------------------------------------------------------->

        <q-input
          v-if="!showPassword"
          filled
          v-model="password"
          type="password"
          label="Password"
        >
          <template v-slot:prepend>
            <q-icon name="lock" color="orange" />
          </template>

          <template v-slot:append>
            <q-icon
              @click="this.showPassword = !this.showPassword"
              name="visibility"
              color="orange"
            /> </template
          >:
        </q-input>

        <!---------------------------------------------------------------------------------->

        <div>
          <q-btn label="LOGIN" type="submit" color="primary" />
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
      username: "",
      password: "",
    };
  },
  methods: {
    async handleSubmit() {
      // eslint-disable-next-line
      console.log(this.username);
      const resp = await api.post("http://localhost:8081/user/login", {
        password: this.password,
        username: this.username,
      });

      //.then((resp) => {
      console.log("adding client");
      console.log();
      console.log(resp.headers["jwt-token"]);
      localStorage.setItem("token", resp.headers["jwt-token"]);
      localStorage.setItem("user_name", this.username);

      window.issignedIn = true;
      //Bus.$emit ( "loggedIn " ) ; // emitting event "Logged In"
      this.$router.push("/");

      //});
      this.username = "";
      this.password = "";
      alert("Done");
      //this.$router.push('/add')
    },
    hello() {
      console.log("visibility button");
    },
    onReset() {
      this.password = "";
      this.username = "";
      requestAnimationFrame(() => {
        this.$refs.observer.reset();
      });
    },
  },
};
</script>
