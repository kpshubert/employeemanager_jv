<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import type { Department } from '../../Types/Department';

const departments = ref<Department[]>([]);
const formData = ref<Department>({ Name: '' });
const isEditMode = ref(false);
const editingId = ref<number | null>(null);

// Fetch all Departments
const fetchDepartments = async () => {

  try {
    const response = await axios.get('http://localhost:8080/api/Department');
    departments.value = response.data;
  } catch (error) {
    console.error('Error fetching departments:', error);
  }
};

// Save (Create or Update)
const saveDepartment = async () => {
  try {
    if (isEditMode.value && editingId.value !== null) {
      await axios.put(`http://localhost:8080/api/Department/${editingId.value}`, formData.value);
    } else {
      await axios.post('http://localhost:8080/api/Department', formData.value);
    }
    resetForm();
    await fetchDepartments();
  } catch (error) {
    console.error('Error saving department:', error);
  }
};

// Edit Department
const editDepartment = (department: Department) => {
  formData.value = { ...department };
  editingId.value = department.id ?? null;
  isEditMode.value = true;
};

// Delete Department
const deleteDepartment = async (id: number) => {
  await axios.delete(`http://localhost:8080/api/Department/${id}`);
  await fetchDepartments();
};

// Reset Form
const resetForm = () => {
  formData.value = { Name: '' };
  isEditMode.value = false;
  editingId.value = null;
};

onMounted(fetchDepartments);
</script>

<template>
  <div>
    <h1>Manage Departments</h1>

    <!-- Form for Add/Edit -->
    <form class="border border-primary border-1 p-1 rounded" @submit.prevent="saveDepartment">
      <div class="row mb-3">
        <div class="col-md-6">
          <label class="form-label">Department Name</label>
          <input class="form-control" v-model="formData.Name" placeholder="Department Name" required />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-md-12">
          <button class="btn btn-sm btn-success" type="submit"><font-awesome-icon v-if=isEditMode :icon="['fas', 'save']" /><font-awesome-icon v-else :icon="['fas', 'plus-square']" />&nbsp;{{ isEditMode ? 'Update' : 'Add' }}</button>
          <button v-if="isEditMode" class="btn btn-sm btn-danger" type="button" @click="resetForm"><font-awesome-icon :icon="['fas', 'ban']" />&nbsp;Cancel</button>
        </div>
      </div>
    </form>

    <!-- List -->
    <table class="table table-striped table-hover">
      <thead>
        <tr>
          <th>
          </th>
          <th>
            Department Name
          </th>
          <th>
            Employee Count
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="department in departments" :key="department.id">
          <td>
            <div class="row">
              <div class="col-md-12">
                <button class="btn btn-sm btn-success" @click="editDepartment(department)"><font-awesome-icon :icon="['fas', 'edit']"/></button><button v-if="department.EmployeeCount == 0" class="btn btn-sm btn-danger" @click="deleteDepartment(department.Id!)"><font-awesome-icon :icon="['fas', 'trash']" /></button>
              </div>
            </div>
          </td>
          <td>
            {{ department.Name }}
          </td>
          <td>
            {{ department.EmployeeCount }}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
