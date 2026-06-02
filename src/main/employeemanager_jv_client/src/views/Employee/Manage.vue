<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import type { Employee } from '../../Types/Employee';
import type { Department } from '../../Types/Department';
import httpClient from '../../utils/httpClient'; // Adjust path as needed
import { useLoading } from 'vue3-loading-overlay';
import '../../../node_modules/vue3-loading-overlay/dist/vue3-loading-overlay.css';
import PhoneInput from 'base-vue-phone-input';
import { Input } from '@/components/ui/input'

const employees = ref<Employee[]>([]);
const departments = ref<Department>([]);
const formData = ref<Employee>({ FirstName: '', LastName: '', Phone: '', Email: '', Department: { Name: '', Id: '' } });
// State for the parsed phone object (emitted by @update event)
const phoneResult = ref<Results | null>(null)
const isEditMode = ref(false);
const editingId = ref<number | null>(null);

const boundPhone = ref('');
const nationalNumber = ref('');

// Fetch all Employees
const fetchEmployees = async () => {
  try {
    // Use the custom axios instance
    const response = await httpClient.get('/Employee');
    employees.value = response.data;
    const departmentResponse = await httpClient.get('/Department');
    departments.value = departmentResponse.data;
  } catch (err) {
    error.value = err as Error;
  }
};

// Save (Create or Update)
const saveEmployee = async () => {
  formData.value.Phone = nationalNumber.value;
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
  boundPhone.value = employee.Phone;
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
  formData.value = { FirstName: '', LastName: '', Phone: '', Email: '', Department: { Name: '', Id:'' }};
  boundPhone.value = '';
  isEditMode.value = false;
  editingId.value = null;
};

// Handler for the @update event to capture structured data
const handlePhoneUpdate = (result: Results) => {
  nationalNumber.value = result.nationalNumber;
  console.log('Phone Valid:', result.isValid)
  console.log('E164 Format:', result.e164)
  console.log('National Number:', result.nationalNumber)
}

onMounted(fetchEmployees);
</script>

<template>
  <!-- Use 'vld-parent' or 'vl-parent' class if not using fullPage -->
  <div>
    <h1>Manage Employees</h1>

    <!-- Form for Add/Edit -->
    <form class="border border-primary border-1 p-1 rounded" @submit.prevent="saveEmployee">
      <div class="row mb-3">
        <div class="col-md-6">
          <label class="form-label">First Name</label><font-awesome-icon class="text-danger" :icon="['fas', 'asterisk']" />
          <input class="form-control" v-model="formData.FirstName" placeholder="First Name" required />
        </div>
        <div class="col-md-6">
          <label class="form-label">Last Name</label><font-awesome-icon class="text-danger" :icon="['fas', 'asterisk']" />
          <input class="form-control" v-model="formData.LastName" placeholder="Last Name" required />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-md-6">
          <div class="phone-input-container">
            <!--
              v-model binds the raw string value.
              @update emits the structured Results object.
              Props configure behavior:
              - preferred-countries: Prioritize specific countries in the dropdown.
              - auto-format: Automatically format numbers as the user types.
            -->
            <label class="form-label">Contact Phone</label><font-awesome-icon class="text-danger" :icon="['fas', 'asterisk']" />
            <PhoneInput
              v-model="boundPhone"
              @update="handlePhoneUpdate"
              :preferred-countries="['US', 'GB', 'FR']"
              auto-format
              placeholder="Enter phone number"
            >
                <template #selector="{ countries, inputValue, updateInputValue }">
                  <!-- Replace the default selector with a custom HTML select element -->
                  <select
                    class="form-select mb-2"
                    :value="inputValue"
                    @change="updateInputValue($event.target.value)"
                  >
                    <option v-for="country in countries" :key="country.iso2" :value="country.iso2">
                      {{ country.name }} (+{{ country.dialCode }})
                    </option>
                  </select>
                </template>

                <!-- Custom Input Slot: Apply your classes here -->
                <template #input="{ inputValue, updateInputValue, placeholder }">
                  <Input
                    ref="phoneInput"
                    class="rounded-e-lg rounded-s-none border-blue-500 bg-gray-50 focus:border-blue-700"
                    type="text"
                    :model-value="inputValue"
                    @input="updateInputValue"
                    :placeholder="placeholder"
                  />
                </template>

            </PhoneInput>
            </div>
        </div>
        <div class="col-md-6">
          <label class="form-label">Email</label><font-awesome-icon class="text-danger" :icon="['fas', 'asterisk']" />
          <input class="form-control" v-model="formData.Email" placeholder="Email Address" required />
        </div>
      </div>
      <div class="row mb-3">>
        <div class="col=md-4">
          <label class="form-label">Department</label><font-awesome-icon class="text-danger" :icon="['fas', 'asterisk']" />
          <select class="form-select" v-model="formData.Department.Id">
            <option value="">--Select a Department--</option>
            <option v-for="department in departments" :key="departments.Id" :value="department.Id">{{department.Name}}</option>
          </select>
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
</template>
