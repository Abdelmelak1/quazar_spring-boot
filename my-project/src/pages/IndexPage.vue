<template>
   <MainLayout />
  <q-space style="min-height: 30px" />

  <h2 style="color:deeppink"><center>Expense Tracker</center></h2>

    <div class="container">
      <h4 style="color:yellowgreen">Your Balance</h4>
      <h1 id="balance">${{this.sumTransactions[0]}}</h1>

      <div class="inc-exp-container">
        <div>
          <h4>Income</h4>
          <p id="money-plus" class="money plus">+${{this.sumTransactions[1]}}</p>
        </div>
        <div>
          <h4>Expense</h4>
          <p id="money-minus" class="money minus">-${{this.sumTransactions[2] * -1}}</p>
        </div>
      </div>
    </div>
 
  
 
</template>

<script>
import { api } from 'src/boot/axios'
import { defineComponent } from 'vue'
import MainLayout from "src/layouts/MainLayout.vue";

export default defineComponent({
  name: 'IndexPage',
  data() {
    return {
      sumTransactions:0,
    }
  },
  components:{
    MainLayout
  },
  async mounted(){
    const resp = await api({
      method: "GET",
      //url: "http://localhost:8081/api/customers",
      url: `http://localhost:8081/api/userAllCustomersTransactionsTrack/${localStorage.getItem("user_name")}`,
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    this.sumTransactions = resp.data;
    console.log(this.sumTransactions);
  }

})
</script>
<style>
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
</style>
