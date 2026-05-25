<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <nav>
    <div class="row">
      <div class="col-md-2">
        <RouterLink class="nav-link p-1 rounded"" to="/">Home</RouterLink>
      </div>
      <div class="col-md-4">
        <RouterLink class="nav-link p-1 rounded" to="/Employee/Manage">Manage Employees</RouterLink>
      </div>
      <div class="col-md-4">
        <RouterLink class="nav-link p-1 rounded" to="/Department/Manage">Manage Departments</RouterLink>
      </div>
      <div class="col-md-2">
        <RouterLink class="nav-link p-1 rounded" to="/About">About</RouterLink>
      </div>
    </div>
  </nav>
  <RouterView />
</template>S
