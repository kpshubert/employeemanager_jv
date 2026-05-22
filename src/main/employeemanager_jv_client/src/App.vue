<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <nav>
    <RouterLink to="/">Home</RouterLink>
    <RouterLink to="/Employee/Manage">Manage Employees</RouterLink>
    <RouterLink to="/About">About</RouterLink>
  </nav>

  <RouterView />
</template>
