<template>
  <MainLayout />
  <q-space style="min-height: 30px" />

  <div class="q-pa-md flex flex-center">
    <div class="q-gutter-y-md column" style="max-width: 100vh">
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
            <q-icon name="face" color="orange" />
          </template>
        </q-input>
        <q-input
          filled
          v-model="lastName"
          type="text"
          label="First Name"
          outlined
        >
          <template v-slot:prepend>
            <q-icon name="person" color="orange" />
          </template>
        </q-input>
        <q-input
          filled
          v-model="firstName"
          type="text"
          label="Last Name"
          outlined
        >
          <template v-slot:prepend>
            <q-icon name="person" color="orange" />
          </template>
        </q-input>

        <q-input filled v-model="email" type="email" label="Email" outlined>
          <template v-slot:prepend>
            <q-icon name="email" color="orange" />
          </template>
        </q-input>

        <q-input
          filled
          v-model="phoneNumber"
          type="phone"
          label="Phone"
          outlined
        >
          <template v-slot:prepend>
            <q-icon name="phone" color="orange" />
          </template>
        </q-input>

        <div>
          <q-btn label="Add" type="submit" color="primary" />
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
import MainLayout from "src/layouts/MainLayout.vue";

export default {
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      username: "",
      phoneNumber: "",
    };
  },
  methods: {
    async handleSubmit() {
      // eslint-disable-next-line
      console.log(this.username);
      try {
        const resp = await api({
          method: "POST",
          //url: "http://localhost:8081/api/customer",
          url: `http://localhost:8081/api/customertouser/${localStorage.getItem(
            "user_name"
          )}`,
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
          data: {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email,
            password: this.password,
            username: this.username,
            phoneNumber: this.phoneNumber,
          },
        });
        this.customers = resp.data;
      } catch (error) {
        console.error(error);
      }
      //.then((resp) => {
      console.log("adding customer");
      console.log();
      //console.log(resp.headers["jwt-token"]);
      //localStorage.setItem('token', resp.headers["jwt-token"]);
      window.issignedIn = true;
      //Bus.$emit ( "loggedIn " ) ; // emitting event "Logged In"
      //this.$router.push("/add");
      //});
      this.username = "";
      this.firstName = "";
      this.lastName = "";
      this.email = "";
      this.password = "";
      this.phoneNumber = "";
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
      this.phoneNumber = "";
      requestAnimationFrame(() => {
        this.$refs.observer.reset();
      });
    },
  },
  components: { MainLayout },
};
</script>
