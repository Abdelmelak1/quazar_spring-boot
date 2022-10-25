<template>
  <q-layout view="lHh Lpr lFf" style="min-height: 6px">
    <q-header elevated>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="toggleLeftDrawer"
        />

        <q-toolbar-title>
          <q-item clickable style="color: blueviolet" to="/"
            ><q-icon name="store" size="lg"
          /></q-item>
        </q-toolbar-title>

        <div>
          <q-btn
            @click="logout"
            class="gt-xs"
            size="12px"
            flat
            dense
            round
            icon="logout"
          ></q-btn>
        </div>
      </q-toolbar>
    </q-header>

    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <q-list>
        <q-item-label header> Navigation </q-item-label>

        <EssentialLink
          v-for="link in essentialLinks"
          :key="link.title"
          v-bind="link"
        />
      </q-list>
    </q-drawer>
  </q-layout>
</template>

<script>
import { defineComponent, ref } from "vue";
import EssentialLink from "components/EssentialLink.vue";
import ClientList from "src/components/ClientList.vue";
import { api } from "src/boot/axios";
const linksList = [
  {
    title: "Home",
    //caption: 'quasar.dev',
    icon: "school",
    link: "/",
  },
  {
    title: "New",
    icon: "login",
    link: "/new",
  },
  {
    title: "Customers List",
    icon: "personadd",
    link: "/add",
  },
];

export default defineComponent({
  name: "MainLayout",
  async created() {
    const resp = await api({
      method: "GET",
      //url: 'http://localhost:8081/api/customers',

      url: `http://localhost:8081/user/userCustomers/${localStorage.getItem(
        "user_name"
      )}`,
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    this.list = resp.data;
    console.log("customers list");
    console.log(this.list);
  },
  methods: {
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("user_name");
      window.location.reload();
      //this.$router.push('/')
    },
  },

  components: {
    EssentialLink,
  },

  setup() {
    const leftDrawerOpen = ref(false);

    return {
      essentialLinks: linksList,
      leftDrawerOpen,
      toggleLeftDrawer() {
        leftDrawerOpen.value = !leftDrawerOpen.value;
      },
    };
  },
});
</script>
<style>
.height {
  min-height: 6px;
}
</style>
