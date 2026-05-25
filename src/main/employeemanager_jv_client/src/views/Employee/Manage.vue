<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import type { Employee } from '../../Types/Employee';
import type { Department } from '../../Types/Department';

const employees = ref<Employee[]>([]);
const departments = ref<Department>([]);
const formData = ref<Employee>({ FirstName: '', LastName: '', Phone: '', Email: '', Department: { Name: '', Id: '' } });
const isEditMode = ref(false);
const editingId = ref<number | null>(null);

// Fetch all Employees
const fetchEmployees = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/Employee');
    employees.value = response.data;
    const departmentResponse = await axios.get('http://localhost:8080/api/Department');
    departments.value = departmentResponse.data;
  } catch (error) {
    console.error('Error fetching employees:', error);
  }
};

// Save (Create or Update)
const saveEmployee = async () => {
  try {
    if (isEditMode.value && editingId.value !== null) {
      await axios.put(`http://localhost:8080/api/Employee/${editingId.value}`, formData.value);
    } else {
      await axios.post('http://localhost:8080/api/Employee', formData.value);
    }
    resetForm();
    await fetchEmployees();
  } catch (error) {
    console.error('Error saving employee:', error);
  }
};

// Edit Employee
const editEmployee = (employee: Employee) => {
  formData.value = { ...employee };
  editingId.value = employee.Id ?? null;
  isEditMode.value = true;
};

// Delete Employee
const deleteEmployee = async (id: number) => {
  await axios.delete(`http://localhost:8080/api/Employee/${id}`);
  await fetchEmployees();
};

// Reset Form
const resetForm = () => {
  formData.value = { FirstName: '', LastName: '', Phone: '', Email: '', Department: { Name: '', Id:'' } };
  isEditMode.value = false;
  editingId.value = null;
};

onMounted(fetchEmployees);
</script>

<template>
  <div>
    <h1>Manage Employees</h1>

    <!-- Form for Add/Edit -->
    <form class="border border-primary border-1 p-1 rounded" @submit.prevent="saveEmployee">
      <div class="row mb-3">
        <div class="col-md-6">
          <label class="form-label">First Name</label>
          <input class="form-control" v-model="formData.FirstName" placeholder="First Name" required />
        </div>
        <div class="col-md-6">
          <label class="form-label">Last Name</label>
          <input class="form-control" v-model="formData.LastName" placeholder="Last Name" required />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-md-6">
          <label class="form-label">Contact Number</label>
          <input class="form-control" v-model="formData.Phone" placeholder="Contact Number" required />
        </div>
        <div class="col-md-6">
          <label class="form-label">Email</label>
          <input class="form-control" v-model="formData.Email" placeholder="Email Address" required />
        </div>
      </div>
      <div class="row mb-3">>
        <div class="col=md-4">
          <label class="form-label">Department</label>
          <select class="form-select" v-model="formData.Department.Id">
            <option value="">--Select a Department--</option>
            <option v-for="department in departments" :key="departments.Id" :value="department.Id">{{department.Name}}</option>
          </select>
z       </div>
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
            First Name
          </th>
          <th>
            Last Name
          </th>
          <th>
            Phone
          </th>
          <th>
            Email
          </th>
          <th>
            Department
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="employee in employees" :key="employee.Id">
          <td>
            <div class="row">
              <div class="col-md-12">
                <button class="btn btn-sm btn-success" @click="editEmployee(employee)"><font-awesome-icon :icon="['fas', 'edit']"/></button><button class="btn  btn-sm btn-danger" @click="deleteEmployee(employee.Id!)"><font-awesome-icon :icon="['fas', 'trash']" /></button>
              </div>
            </div>
          </td>
          <td>
            {{ employee.FirstName }}
          </td>
          <td>
            {{ employee.LastName }}
          </td>
          <td>
            {{ employee.Phone }}
          </td>
          <td>
            {{ employee.Email}}
          </td>
          <td>
            {{ employee.Department.Name}}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>S
