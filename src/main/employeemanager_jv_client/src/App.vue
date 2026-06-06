<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import 'bulma/css/bulma.min.css';
import 'rms-vue3-confirm-dialog/dist/rms-vue3-confirm-dialog.css';
import { showDialog } from 'rms-vue3-confirm-dialog';</script>

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
</template>
