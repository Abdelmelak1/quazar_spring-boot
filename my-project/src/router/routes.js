





const routes = [
  
  {
    path: '/main',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/IndexPage.vue') }
    ],
    meta: {auth:true}
  },
  //add client
  {
    path: '/add',
    component: () => import('pages/AddClientPage.vue'),
    meta: {auth:true}
    
  },
  {
    path: '/new',
    component: () => import('pages/NewClientPage.vue'),
    meta: {auth:true}
  },
  {
    path: '/login',
    component: () => import('pages/LoginPage.vue'),
    meta: {auth:false}
  },
  {
    path: '/signup',
    component: () => import('pages/SignUpPage.vue'),
    meta: {auth:false}
  },

   //add client 2
   {
    path: '/',
    component: () => import('pages/IndexPage.vue'),
    //beforeEnter: authenticationGuard,
    meta: {auth:true}
    
  },
 

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
    
  }
]

export default routes
