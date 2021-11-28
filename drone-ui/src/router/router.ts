import { createWebHistory, createRouter } from "vue-router";
import TheMap from "@/components/TheMap.vue";

const routes = [
    {path: '/', component: TheMap},
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;