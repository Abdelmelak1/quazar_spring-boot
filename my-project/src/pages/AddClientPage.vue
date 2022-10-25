<template>
  <div id="q-app">
    <div class="q-pa-md">
      <div class="row q-col-gutter-sm">
        <!-- info dialog-->
        <q-dialog v-model="promptInfo" persistent>
          <q-card>
            <div id="q-app" style="min-height: 100vh">
              <div class="q-pa-md q-gutter-md">
                <q-btn
                  dense
                  round
                  flat
                  color="red"
                  @click="quit(props)"
                  icon="cancel"
                ></q-btn>
                <!-- Customer expense track  -->
                <div class="container">
                  
                  <h6 id="balance">${{this.transactionsFeed[0]}}</h6>

                  <div class="inc-exp-container">
                    <div>
                      <p id="money-plus" class="money plus">
                        +${{this.transactionsFeed[1]}}
                      </p>
                    </div>
                    <div>
                      <p id="money-minus" class="money minus">
                        -${{this.transactionsFeed[2] * -1}}
                      </p>
                    </div>
                  </div>
                </div>
                <!-- Customer expense track  -->
                <q-list
                  bordered
                  class="rounded-borders"
                  style="max-width: 600px"
                >
                  <q-table
                    title="Transactions"
                    :rows="transactionsC"
                    :columns="columnsTnx"
                    row-key="name"
                    dense
                  >
                    <template v-slot:body="props">
                      <q-tr :props="props">
                        <q-td key="description" :props="props">
                          {{ props.row.description }}
                        </q-td>

                        <q-td key="amount" :props="props">
                          {{ props.row.amount }}
                        </q-td>

                        <q-td
                          style="justify-content: center"
                          key="lastName"
                          :props="props"
                        >
                          <q-btn
                            dense
                            round
                            flat
                            color="red"
                            @click="deleteTransaction(props)"
                            icon="delete"
                          ></q-btn>
                        </q-td>

                        <q-td key="lastName" :props="props">
                          <q-btn
                            dense
                            round
                            flat
                            color="blue"
                            @click="editRow(props)"
                            icon="edit"
                          ></q-btn>
                        </q-td>
                      </q-tr>
                    </template>
                  </q-table>

                  <!--
      <q-list>
  
      <EssentialLink
          v-for="link in clients"
          :key="link.nom"
          v-bind="link"
        />
      </q-list>
      
-->
                </q-list>
              </div>
            </div>
          </q-card>
        </q-dialog>
        <!-- end info dialog-->
        <!-- add transaction -->
        <q-dialog v-model="promptTransaction" persistent>
          <q-card>
            <q-form
              @submit="handleTransaction(onSubmit)"
              @reset="onReset"
              class="q-gutter-md"
            >
              <q-input
                filled
                v-model="amount"
                type="number"
                label="Amount"
                outlined
              >
                <template v-slot:prepend>
                  <q-icon name="money" color="green" />
                </template>
              </q-input>

              <q-input
                filled
                v-model="description"
                type="text"
                label="Description"
                outlined
              >
                <template v-slot:prepend>
                  <q-icon name="description" color="pink" />
                </template>
              </q-input>

              <div>
                <q-btn label="Add" type="submit" color="primary" />
                <q-btn
                  label="Cancel"
                  type="reset"
                  color="primary"
                  flat
                  class="q-ml-sm"
                />
              </div>
            </q-form>
          </q-card>
        </q-dialog>
        <!-- end add transaction -->
        <!--dialog   update    -->

        <!-- prompt-->
        <q-dialog v-model="prompt" persistent>
          <q-card>
            <q-form
              @submit="handleSubmit(onSubmit)"
              @reset="onReset"
              class="q-gutter-md"
            >
              <q-input
                filled
                v-model="lastName"
                type="text"
                label="Last Name"
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
                label="First Name"
                outlined
              >
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

              <!-- <q-input
     bg-color="white"
    -->

              <div>
                <q-btn label="Update" type="submit" color="primary" />
                <q-btn
                  label="Cancel"
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
          </q-card>
        </q-dialog>

        <!-- end dialog-->

        <div class="col">
          <MainLayout />
          <q-space style="min-height: 30px" />

          <router-link to="/new" class="none">
            <q-btn dense round flat color="green" icon="add" size="2em"></q-btn>
          </router-link>

          <div class="betwwen">
            <q-space />
            <q-input
              borderless
              dense
              debounce="300"
              v-model="filter"
              placeholder="Search"
            >
              <template v-slot:append>
                <q-icon name="search" />
              </template>
            </q-input>

            <q-select
              v-model="visibleColumns"
              multiple
              outlined
              dense
              options-dense
              :display-value="$q.lang.table.columns"
              emit-value
              map-options
              :options="columns"
              option-value="name"
              options-cover
              style="min-width: 150px"
            />
          </div>
          <q-table
            :filter="filter"
            title="Customers"
            :rows="customers"
            :columns="columns"
            :visible-columns="visibleColumns"
            row-key="name"
            dense
          >
            <template v-slot:body="props">
              <q-tr :props="props">
                <q-td key="lastName" :props="props">
                  {{ props.row.lastName }}
                </q-td>

                <q-td key="firstName" :props="props">
                  {{ props.row.firstName }}
                </q-td>

                <q-td key="email" :props="props">
                  {{ props.row.email }}
                </q-td>

                <q-td key="phoneNumber" :props="props">
                  {{ props.row.phoneNumber }}
                </q-td>

                <q-td
                  style="justify-content: center"
                  key="lastName"
                  :props="props"
                >
                  <q-btn
                    dense
                    round
                    flat
                    color="red"
                    @click="deleteRow(props)"
                    icon="delete"
                  ></q-btn>
                </q-td>

                <q-td key="lastName" :props="props">
                  <q-btn
                    dense
                    round
                    flat
                    color="blue"
                    @click="editRow(props)"
                    icon="edit"
                  ></q-btn>
                </q-td>

                <q-td key="lastName" :props="props">
                  <q-btn
                    dense
                    round
                    flat
                    color="green"
                    @click="newTransaction(props)"
                    icon="add"
                  ></q-btn>
                  <q-btn
                    dense
                    round
                    flat
                    color="pink"
                    @click="getTransactions(props)"
                    icon="info"
                  ></q-btn>
                </q-td>
              </q-tr>
            </template>
            <!-- Actions buttons(not working)
              <template v-slot:body-cell-actions="props">
                <q-td :props="props">
                  <q-btn dense round flat color="grey" @click="editRow(props)" icon="edit"></q-btn>
                  <q-btn dense round flat color="grey" @click="deleteRow(props)" icon="delete"></q-btn>
                </q-td>          
              </template>
              -->
          </q-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, ref } from "vue";
import { api } from "src/boot/axios";
import MainLayout from "src/layouts/MainLayout.vue";

function newTransaction(props) {
  this.id = props.row["id"];
  console.log(this.id);
  this.promptTransaction = true;
}
function changeColor(props) {
  if (props.amount > 0) return "greenColor";
  return "redColor";
}
function editRow(props) {
  this.id = props.row["id"];
  console.log(this.id);
  this.prompt = true;
  //this.$router.push('/add')
  //window.location.reload();
}
async function getTransactions(props) {
  this.promptInfo = true;
  this.id = props.row["id"];
  console.log("id", this.id);
  const resp = await api({
    method: "GET",
    url: `http://localhost:8081/api/customertouser/${this.id}`,
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
  this.transactionsC = resp.data;
  console.log("transactionsC");
  console.log(this.transactionsC);
  
  
  const resp1 = await api({
  method: "GET",
  url: `http://localhost:8081/api/sumTransactions/${this.id}`,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("token")}`,
  },
});

this.transactionsFeed = resp1.data;
  console.log("transactionsFeed");
  console.log(this.transactionsFeed);
  
}

function deleteRow(props) {
  this.id = props.row["id"];
  //this.putURL = `http://localhost:9080/SpringRest/clients/update-client/${this.id} `;

  console.log(this.id);
  //if confirmed continue
  api.delete(`http://localhost:8081/api/customer/${this.id}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
  window.location.reload();
}

function updateClient() {
  $refs.updateDialog.show();
  //alert("$refs.updateDialog")
  console.log(this.name);
  return "hello";
}
function quit() {
  this.promptInfo = false;
}

export default defineComponent({
  name: "AddClientPage",
  methods: {
    async handleTransaction() {
      const resp = await api({
        method: "POST",
        url: `http://localhost:8081/api/transaction/${this.id}`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        data: {
          amount: this.amount,
          description: this.description,
        },
      });
      console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      console.log(this.id);
      this.promptTransaction = false;
    },
    async handleSubmit() {
      const resp = await api({
        method: "PUT",
        url: `http://localhost:8081/api/customer/${this.id}`,
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

      this.prompt = false;
      window.location.reload();
      console.log("putURL" + this.putURL);
    },
    onReset() {
      this.email = "";
      this.password = "";
      this.lastName = "";
      this.firstName = "";
      this.phoneNumber = "";
      this.prompt = false;
      this.promptTransaction = false;
      this.promptInfo = false;
      requestAnimationFrame(() => {
        this.$refs.observer.reset();
      });
    },
    changeColor,
    quit,
    getTransactions,
    deleteRow,
    editRow,
    newTransaction,
    updateClient,
    reloadPage() {
      window.location.reload();
    },
  },
  data() {
    return {
      customers: undefined,
      transactionsC: undefined,
      transactionsFeed:undefined,
      id: "",
      putURL: "",
      firstName: "",
      lastName: "",
      phoneNumber: "",
      transactions: "",
      amount: "",
      description: "",
      columnsTnx: [
        {
          label: "Description",
          field: "description",
          name: "description",
          align: "left",
          sortable: true,
        },
        {
          label: "Amount",
          field: "amount",
          name: "amount",
          align: "left",
          sortable: true,
        },
      ],
      columns: [
        {
          label: "Last Name",
          field: "lastName",
          name: "lastName",
          align: "left",
          sortable: true,
        },
        {
          label: "First Name",
          field: "firstName",
          name: "firstName",
          align: "left",
          sortable: true,
        },
        {
          label: "Email",
          field: "email",
          name: "email",
          align: "left",
          sortable: true,
        },
        {
          label: "Phone Number",
          field: "phoneNumber",
          name: "phoneNumber",
          align: "center",
          sortable: true,
        },
        {
          label: "Delete",
          field: "id",
          name: "id",
          align: "center",
          format: (age) => `${age} years`,
          sortable: true,
        },
        {
          label: "Modify",
          field: "id",
          name: "id",
          align: "center",
          format: (age) => `${age} years`,
          sortable: true,
        },
        {
          label: "Transactions",
          field: "transactions",
          name: "transactions",
          align: "center",
          sortable: true,
        },
      ],
    };
  },
  setup() {
    const selected = ref();
    return {
      filter: ref(""),
      selected,
      prompt: ref(false),
      promptTransaction: ref(false),
      promptInfo: ref(false),
      name: String,
      visibleColumns: ref([
        "lastName",
        "firstName",
        "email",
        "phoneNumber",
        "transactions",
        "Modify",
        "Delete",
      ]),
      address: ref(""),
    };
  },
  async mounted() {
    const resp = await api({
      method: "GET",
      //url: "http://localhost:8081/api/customers",
      url: `http://localhost:8081/user/userCustomers/${localStorage.getItem(
        "user_name"
      )}`,
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    this.customers = resp.data;
    console.log(
      "username : ",
      localStorage.getItem("user_name"),
      "currentName : ",
      this.username
    );
  },

  components: { MainLayout },
});
</script>

<style>
.none {
  text-decoration: none;
}
.betwwen {
  display: flex;
  align-content: space-between;
}
.redColor {
  background: red;
}
.greenColor {
  background: green;
}



/*Customer expenses */
.container {
  margin: 30px auto;
  width: 350px;
}
.inc-exp-container {
  background-color: #fff;
  box-shadow: var(--box-shadow);
  padding: 20px;
  display: flex;
  justify-content: space-between;
  margin: 20px 0;
}

.inc-exp-container > div {
  flex: 1;
  text-align: center;
}

.inc-exp-container > div:first-of-type {
  border-right: 1px solid #dedede;
}
.money {
  font-size: 20px;
  letter-spacing: 1px;
  margin: 5px 0;
}

.money.plus {
  color: #2ecc71;
}

.money.minus {
  color: #c0392b;
}

#balance{
  text-align: center;
}
</style>

