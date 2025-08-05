$(document).ready(function () {
    let currentPage = 0;
    let pageSize = 10;
    let sortBy = 'id';
    let sortDir = 'asc';
    let currentSearchKeyword = '';
    let isSearchMode = false;

    // API Configuration - Match your backend CORS settings
    const API_BASE_URL = 'http://localhost:8080/api/v1/job';

    // Test API connection on page load
    testAPIConnection();

    // Load jobs when page loads
    loadJobs();

    // Test API Connection
    function testAPIConnection() {
        console.log("🔍 Testing API connection...");

        fetch(`${API_BASE_URL}/loadall`, {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                console.log("API Response Status:", response.status);
                if (response.ok) {
                    console.log("✅ API Connection successful!");
                    return response.json();
                } else {
                    console.error("❌ API Connection failed:", response.status);
                    throw new Error(`API Error: ${response.status}`);
                }
            })
            .then(data => {
                console.log("✅ Sample API data:", data);
            })
            .catch(error => {
                console.error("❌ API Connection test failed:", error);
                showAlert(`❌ Cannot connect to API!\n\nPlease check:\n1. Backend server is running on port 8080\n2. CORS is configured correctly\n\nError: ${error.message}`, 'error');
            });
    }

    // Enhanced alert function
    function showAlert(message, type = 'info') {
        if (typeof alert !== 'undefined') {
            alert(message);
        } else {
            console.log(`${type.toUpperCase()}: ${message}`);
        }
    }

    // Page size change event
    $("#pageSizeSelect").change(function() {
        pageSize = parseInt($(this).val());
        currentPage = 0;
        if (isSearchMode) {
            searchJobs(currentSearchKeyword);
        } else {
            loadJobs();
        }
    });

    // Sort change events
    $("#sortBySelect, #sortOrderSelect").change(function() {
        sortBy = $("#sortBySelect").val();
        sortDir = $("#sortOrderSelect").val();
        currentPage = 0;
        if (isSearchMode) {
            searchJobs(currentSearchKeyword);
        } else {
            loadJobs();
        }
    });

    // Search functionality with debounce
    let searchTimeout;
    $("#searchInput").on('keyup', function () {
        clearTimeout(searchTimeout);
        let searchTerm = $(this).val().trim();

        searchTimeout = setTimeout(function() {
            currentSearchKeyword = searchTerm;
            currentPage = 0;

            if (searchTerm === '') {
                isSearchMode = false;
                loadJobs();
            } else {
                isSearchMode = true;
                searchJobs(searchTerm);
            }
        }, 500);
    });

    // Save Job Button Click Event - Backend field names: job_title, job_description
    $("#saveJobBtn").click(function () {
        console.log("💾 Save button clicked");

        // Get form values
        const jobTitle = $("#jobTitle").val()?.trim() || '';
        const companyName = $("#companyName").val()?.trim() || '';
        const jobLocation = $("#jobLocation").val()?.trim() || '';
        const jobType = $("#jobType").val() || 'Full-time';
        const jobDescription = $("#jobDescription").val()?.trim() || '';

        // Validation
        if (!jobTitle || !companyName || !jobLocation) {
            showAlert("Please fill in all required fields (Job Title, Company Name, Location)!", 'error');
            return;
        }

        // Backend expects these exact field names
        let jobData = {
            jobtitle: jobTitle,        // Backend DTO field name
            company: companyName,
            location: jobLocation,
            type: jobType,
            jobdescription: jobDescription,  // Backend DTO field name
            status: "Active"
        };

        console.log("📤 Saving job with backend field names:", jobData);

        // Show loading state
        const saveBtn = $("#saveJobBtn");
        saveBtn.prop('disabled', true).text('Saving...');

        // Make API call to backend create endpoint
        fetch(`${API_BASE_URL}/create`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(jobData)
        })
            .then(response => {
                console.log("📝 Save response status:", response.status);

                if (!response.ok) {
                    return response.text().then(errorText => {
                        console.error("❌ Save error response:", errorText);
                        throw new Error(`Server Error ${response.status}: ${errorText || response.statusText}`);
                    });
                }

                // Backend returns APIResponse format
                return response.json();
            })
            .then(data => {
                console.log("✅ Save success:", data);

                // Check APIResponse format from backend
                if (data.status === 201 || data.status === 200) {
                    showAlert(`✅ ${data.message}`, 'success');
                } else {
                    showAlert("✅ Job saved successfully!", 'success');
                }

                // Refresh the jobs list
                loadJobs();

                // Clear form and close modal
                clearAddJobForm();
                $('#addJobModal').modal('hide');
            })
            .catch(error => {
                console.error("❌ Save error:", error);
                showAlert(`❌ Error saving job!\n\n${error.message}\n\nPlease check:\n1. All required fields are filled\n2. Server is running on http://localhost:8080\n3. Field validation passed`, 'error');
            })
            .finally(() => {
                saveBtn.prop('disabled', false).text('Save Job');
            });
    });

    // Update Job Button Click Event - Backend field names
    $("#updateJobBtn").click(function () {
        console.log("✏️ Update button clicked");

        // Get form values
        const jobId = $("#editJobId").val();
        const jobTitle = $("#editJobTitle").val()?.trim() || '';
        const companyName = $("#editCompanyName").val()?.trim() || '';
        const jobLocation = $("#editJobLocation").val()?.trim() || '';
        const jobType = $("#editJobType").val() || 'Full-time';
        const jobDescription = $("#editJobDescription").val()?.trim() || '';

        // Validation
        if (!jobTitle || !companyName || !jobLocation) {
            showAlert("Please fill in all required fields (Job Title, Company Name, Location)!", 'error');
            return;
        }

        if (!jobId || isNaN(parseInt(jobId))) {
            showAlert("Invalid job ID. Please refresh and try again.", 'error');
            return;
        }

        // Backend expects these exact field names
        let jobData = {
            id: parseInt(jobId),
            jobtitle: jobTitle,        // Backend DTO field name
            company: companyName,
            location: jobLocation,
            type: jobType,
            jobdescription: jobDescription,  // Backend DTO field name
            status: "Active"
        };

        console.log("📤 Updating job with backend field names:", jobData);

        // Show loading state
        const updateBtn = $("#updateJobBtn");
        updateBtn.prop('disabled', true).text('Updating...');

        fetch(`${API_BASE_URL}/update`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(jobData)
        })
            .then(response => {
                console.log("📝 Update response status:", response.status);

                if (!response.ok) {
                    return response.text().then(errorText => {
                        console.error("❌ Update error response:", errorText);
                        throw new Error(`Server Error ${response.status}: ${errorText || response.statusText}`);
                    });
                }

                // Backend returns APIResponse format
                return response.json();
            })
            .then(data => {
                console.log("✅ Update success:", data);

                // Check APIResponse format from backend
                if (data.status === 200) {
                    showAlert(`✅ ${data.message}`, 'success');
                } else {
                    showAlert("✅ Job updated successfully!", 'success');
                }

                // Refresh the jobs list
                loadJobs();

                $('#editJobModal').modal('hide');
            })
            .catch(error => {
                console.error("❌ Update error:", error);

                if (error.message.includes('404') || error.message.includes('Job not found')) {
                    showAlert(`❌ Job not found!\n\nThe job with ID ${jobData.id} does not exist.\nPlease refresh the page and try again.`, 'error');
                    loadJobs();
                } else {
                    showAlert(`❌ Error updating job!\n\n${error.message}\n\nPlease check:\n1. Job ID exists\n2. All required fields are filled\n3. Server is running`, 'error');
                }
            })
            .finally(() => {
                updateBtn.prop('disabled', false).text('Save Changes');
            });
    });

    // Load Jobs Function - Handle backend APIResponse format
    function loadJobs() {
        console.log("📋 Loading jobs...");

        // Show loading indicator
        $("#jobsTableBody").html(`
            <tr>
                <td colspan="8" class="text-center py-4">
                    <div class="spinner-border spinner-border-sm me-2" role="status"></div>
                    Loading jobs...
                </td>
            </tr>
        `);

        fetch(`${API_BASE_URL}/loadall`, {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                console.log("📝 Load response status:", response.status);

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log("📥 Loaded data from backend:", data);

                // Backend returns APIResponse format: {status: 200, message: "Success", data: [...]}
                let jobsArray = [];

                if (data.data && Array.isArray(data.data)) {
                    jobsArray = data.data;
                } else if (Array.isArray(data)) {
                    jobsArray = data;
                } else {
                    console.error("Unexpected data format:", data);
                    jobsArray = [];
                }

                console.log("📊 Extracted jobs array:", jobsArray);

                if (!jobsArray || jobsArray.length === 0) {
                    displayJobs([]);
                    updatePaginationInfo(createEmptyPageData());
                    updatePaginationControls(createEmptyPageData());
                    return;
                }

                // Apply client-side sorting (since backend loadall doesn't use pagination)
                jobsArray = sortJobsArray(jobsArray, sortBy, sortDir);

                // Create pagination data
                const totalElements = jobsArray.length;
                const totalPages = Math.ceil(totalElements / pageSize);
                const startIndex = currentPage * pageSize;
                const endIndex = startIndex + pageSize;
                const paginatedJobs = jobsArray.slice(startIndex, endIndex);

                const pageData = {
                    content: paginatedJobs,
                    number: currentPage,
                    size: pageSize,
                    totalElements: totalElements,
                    totalPages: totalPages,
                    first: currentPage === 0,
                    last: currentPage >= totalPages - 1
                };

                displayJobs(pageData.content, startIndex);
                updatePaginationInfo(pageData);
                updatePaginationControls(pageData);
            })
            .catch(error => {
                console.error("❌ Load error:", error);
                showAlert(`❌ Error loading jobs!\n\n${error.message}\n\nPlease check:\n1. Backend server is running\n2. API endpoint /loadall exists\n3. CORS is configured`, 'error');
                displayJobs([]);
                updatePaginationInfo(createEmptyPageData());
                updatePaginationControls(createEmptyPageData());
            });
    }

    // Sort jobs array - Handle backend field names
    function sortJobsArray(jobs, sortBy, sortDir) {
        return jobs.sort((a, b) => {
            let aVal, bVal;

            switch(sortBy) {
                case 'jobTitle':
                    aVal = (a.jobtitle || '').toLowerCase();  // Backend uses job_title
                    bVal = (b.jobtitle || '').toLowerCase();
                    break;
                case 'company':
                    aVal = (a.company || '').toLowerCase();
                    bVal = (b.company || '').toLowerCase();
                    break;
                case 'location':
                    aVal = (a.location || '').toLowerCase();
                    bVal = (b.location || '').toLowerCase();
                    break;
                case 'type':
                    aVal = (a.type || '').toLowerCase();
                    bVal = (b.type || '').toLowerCase();
                    break;
                default: // id
                    aVal = a.id || 0;
                    bVal = b.id || 0;
            }

            if (aVal < bVal) return sortDir === 'asc' ? -1 : 1;
            if (aVal > bVal) return sortDir === 'asc' ? 1 : -1;
            return 0;
        });
    }

    // Create empty page data
    function createEmptyPageData() {
        return {
            content: [],
            number: 0,
            size: pageSize,
            totalElements: 0,
            totalPages: 0,
            first: true,
            last: true
        };
    }

    // Search Jobs - Handle backend APIResponse format
    function searchJobs(keyword) {
        console.log("🔍 Searching for:", keyword);

        // Show loading indicator
        $("#jobsTableBody").html(`
            <tr>
                <td colspan="8" class="text-center py-4">
                    <div class="spinner-border spinner-border-sm me-2" role="status"></div>
                    Searching jobs...
                </td>
            </tr>
        `);

        fetch(`${API_BASE_URL}/search/${encodeURIComponent(keyword)}`, {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log("🔍 Search results from backend:", data);

                // Backend returns APIResponse format for search
                let jobsArray = [];

                if (data.data && Array.isArray(data.data)) {
                    jobsArray = data.data;
                } else if (Array.isArray(data)) {
                    jobsArray = data;
                } else {
                    console.error("Unexpected search data format:", data);
                    jobsArray = [];
                }

                if (!jobsArray || jobsArray.length === 0) {
                    displayJobs([]);
                    updatePaginationInfo(createEmptyPageData());
                    updatePaginationControls(createEmptyPageData());
                    return;
                }

                // Apply client-side sorting and pagination
                jobsArray = sortJobsArray(jobsArray, sortBy, sortDir);

                const totalElements = jobsArray.length;
                const totalPages = Math.ceil(totalElements / pageSize);
                const startIndex = currentPage * pageSize;
                const endIndex = startIndex + pageSize;
                const paginatedJobs = jobsArray.slice(startIndex, endIndex);

                const pageData = {
                    content: paginatedJobs,
                    number: currentPage,
                    size: pageSize,
                    totalElements: totalElements,
                    totalPages: totalPages,
                    first: currentPage === 0,
                    last: currentPage >= totalPages - 1
                };

                displayJobs(pageData.content, startIndex);
                updatePaginationInfo(pageData);
                updatePaginationControls(pageData);
            })
            .catch(error => {
                console.error("❌ Search error:", error);
                showAlert(`❌ Error searching jobs!\n\n${error.message}`, 'error');
                displayJobs([]);
                updatePaginationInfo(createEmptyPageData());
                updatePaginationControls(createEmptyPageData());
            });
    }

    // Display Jobs in Table - Handle backend field names
    function displayJobs(jobs, startIndex = 0) {
        console.log("📊 Displaying jobs:", jobs.length);

        let tbody = $("#jobsTableBody");
        tbody.empty();

        if (!jobs || jobs.length === 0) {
            tbody.append(`
                <tr>
                    <td colspan="8" class="text-center py-4">
                        <div class="text-muted">
                            <i class="fas fa-inbox fa-2x mb-3 d-block"></i>
                            <p class="mb-0">No jobs found</p>
                        </div>
                    </td>
                </tr>
            `);
            return;
        }

        jobs.forEach((job, index) => {
            // Backend uses job_title and job_description (underscore format)
            const jobId = job.id || (startIndex + index + 1);
            const jobTitle = job.jobtitle || 'N/A';               // Backend field name
            const jobDescription = job.jobdescription || 'No description';  // Backend field name
            const company = job.company || 'N/A';
            const location = job.location || 'N/A';
            const status = job.status || 'Active';
            const type = job.type || 'N/A';

            tbody.append(`
                <tr>
                    <td>${startIndex + index + 1}</td>
                    <td>${escapeHtml(company)}</td>
                    <td>${escapeHtml(jobDescription)}</td>
                    <td>${escapeHtml(jobTitle)}</td>
                    <td>${escapeHtml(location)}</td>
                    <td>
                        <span class="badge ${status === 'Active' ? 'bg-success' : 'bg-secondary'}">
                            ${escapeHtml(status)}
                        </span>
                    </td>
                    <td>${escapeHtml(type)}</td>
                    <td>
                        <button class="btn btn-sm btn-warning me-1 edit-btn" 
                                data-id="${jobId}" 
                                data-title="${escapeHtml(jobTitle)}" 
                                data-company="${escapeHtml(company)}" 
                                data-location="${escapeHtml(location)}" 
                                data-type="${escapeHtml(type)}" 
                                data-description="${escapeHtml(jobDescription)}"
                                data-bs-toggle="modal" 
                                data-bs-target="#editJobModal">
                            Edit
                        </button>
                        <button class="btn btn-sm btn-danger delete-btn" data-id="${jobId}">
                            Delete
                        </button>
                    </td>
                </tr>
            `);
        });

        console.log("✅ Table populated with", jobs.length, "jobs");
    }

    // Escape HTML to prevent XSS
    function escapeHtml(text) {
        if (text == null) return '';
        return String(text)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#039;');
    }

    // Update Pagination Info
    function updatePaginationInfo(pageData) {
        const paginationInfo = $("#paginationInfo");

        if (!pageData || pageData.totalElements === 0) {
            paginationInfo.text("No jobs found");
            return;
        }

        const start = (pageData.number * pageData.size) + 1;
        const end = Math.min(start + pageData.size - 1, pageData.totalElements);

        paginationInfo.text(`Showing ${start} to ${end} of ${pageData.totalElements} jobs`);
    }

    // Update Pagination Controls
    function updatePaginationControls(pageData) {
        const paginationControls = $("#paginationControls");
        paginationControls.empty();

        if (!pageData || pageData.totalPages <= 1) {
            return;
        }

        // Previous button
        const prevDisabled = pageData.first ? 'disabled' : '';
        paginationControls.append(`
            <li class="page-item ${prevDisabled}">
                <a class="page-link" href="#" data-page="${pageData.number - 1}">Previous</a>
            </li>
        `);

        // Page numbers
        const startPage = Math.max(0, pageData.number - 2);
        const endPage = Math.min(pageData.totalPages - 1, pageData.number + 2);

        // First page if not in range
        if (startPage > 0) {
            paginationControls.append(`
                <li class="page-item">
                    <a class="page-link" href="#" data-page="0">1</a>
                </li>
            `);
            if (startPage > 1) {
                paginationControls.append(`<li class="page-item disabled"><span class="page-link">...</span></li>`);
            }
        }

        // Page numbers in range
        for (let i = startPage; i <= endPage; i++) {
            const activeClass = i === pageData.number ? 'active' : '';
            paginationControls.append(`
                <li class="page-item ${activeClass}">
                    <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                </li>
            `);
        }

        // Last page if not in range
        if (endPage < pageData.totalPages - 1) {
            if (endPage < pageData.totalPages - 2) {
                paginationControls.append(`<li class="page-item disabled"><span class="page-link">...</span></li>`);
            }
            paginationControls.append(`
                <li class="page-item">
                    <a class="page-link" href="#" data-page="${pageData.totalPages - 1}">${pageData.totalPages}</a>
                </li>
            `);
        }

        // Next button
        const nextDisabled = pageData.last ? 'disabled' : '';
        paginationControls.append(`
            <li class="page-item ${nextDisabled}">
                <a class="page-link" href="#" data-page="${pageData.number + 1}">Next</a>
            </li>
        `);
    }

    // Pagination click event
    $(document).on('click', '.page-link', function(e) {
        e.preventDefault();

        if ($(this).parent().hasClass('disabled') || $(this).parent().hasClass('active')) {
            return;
        }

        const page = parseInt($(this).data('page'));
        if (!isNaN(page)) {
            currentPage = page;

            if (isSearchMode) {
                searchJobs(currentSearchKeyword);
            } else {
                loadJobs();
            }
        }
    });

    // Edit Button Click Event (Dynamic)
    $(document).on('click', '.edit-btn', function () {
        let jobId = $(this).data('id');
        let jobTitle = $(this).data('title');
        let company = $(this).data('company');
        let location = $(this).data('location');
        let type = $(this).data('type');
        let description = $(this).data('description');

        console.log("✏️ Editing job:", {jobId, jobTitle, company, location, type, description});

        $("#editJobId").val(jobId);
        $("#editJobTitle").val(jobTitle);
        $("#editCompanyName").val(company);
        $("#editJobLocation").val(location);
        $("#editJobType").val(type);
        $("#editJobDescription").val(description);
    });

    // Delete Button Click Event (Dynamic) - Use backend changestatus endpoint
    $(document).on('click', '.delete-btn', function () {
        let jobId = $(this).data('id');
        let jobTitle = $(this).closest('tr').find('td:nth-child(4)').text();

        console.log("🗑️ Delete requested for job:", {jobId, jobTitle});

        if (confirm(`Are you sure you want to deactivate the job "${jobTitle}"?`)) {
            changeJobStatus(jobId);
        }
    });

    // Change Job Status Function - Use backend changestatus endpoint
    function changeJobStatus(jobId) {
        console.log("🔄 Changing status for job ID:", jobId);

        if (!jobId || isNaN(parseInt(jobId))) {
            showAlert("Invalid job ID", 'error');
            return;
        }

        // Show loading state on the delete button
        const deleteBtn = $(`.delete-btn[data-id="${jobId}"]`);
        deleteBtn.prop('disabled', true).text('Processing...');

        fetch(`${API_BASE_URL}/changestatus/${jobId}`, {
            method: "PATCH",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                console.log("📝 Status change response:", response.status);

                if (!response.ok) {
                    return response.text().then(errorText => {
                        console.error("❌ Status change error:", errorText);
                        throw new Error(`Server Error ${response.status}: ${errorText || response.statusText}`);
                    });
                }

                // Backend returns APIResponse format
                return response.json();
            })
            .then(data => {
                console.log("✅ Status change success:", data);

                // Check APIResponse format from backend
                if (data.status === 200) {
                    showAlert(`✅ ${data.message}`, 'success');
                } else {
                    showAlert("✅ Job status changed successfully!", 'success');
                }

                // Refresh the jobs list
                loadJobs();
            })
            .catch(error => {
                console.error("❌ Status change error:", error);

                if (error.message.includes('404') || error.message.includes('not found')) {
                    showAlert(`❌ Job not found!\n\nThe job with ID ${jobId} does not exist.\nRefreshing the job list...`, 'error');
                    loadJobs();
                } else {
                    showAlert(`❌ Error changing job status!\n\n${error.message}\n\nPlease check:\n1. Job ID exists\n2. Server is running\n3. API endpoint /changestatus/{id} exists`, 'error');
                }
            })
            .finally(() => {
                deleteBtn.prop('disabled', false).text('Delete');
            });
    }

    // Clear Add Job Form
    function clearAddJobForm() {
        $("#jobTitle").val('');
        $("#companyName").val('');
        $("#jobLocation").val('');
        $("#jobType").val('Full-time');
        $("#jobDescription").val('');
        console.log("🧹 Add job form cleared");
    }

    // Clear Edit Job Form
    function clearEditJobForm() {
        $("#editJobId").val('');
        $("#editJobTitle").val('');
        $("#editCompanyName").val('');
        $("#editJobLocation").val('');
        $("#editJobType").val('Full-time');
        $("#editJobDescription").val('');
        console.log("🧹 Edit job form cleared");
    }

    // Modal event handlers
    $('#addJobModal').on('hidden.bs.modal', function () {
        clearAddJobForm();
    });

    $('#editJobModal').on('hidden.bs.modal', function () {
        clearEditJobForm();
    });

    // Show modal event handlers for debugging
    $('#addJobModal').on('show.bs.modal', function () {
        console.log("📝 Add job modal opening");
        clearAddJobForm();
    });

    $('#editJobModal').on('show.bs.modal', function () {
        console.log("✏️ Edit job modal opening");
    });

    // Form validation for Enter key
    $("#addJobForm input, #addJobForm textarea, #addJobForm select").on('keypress', function(e) {
        if (e.which === 13 && !e.shiftKey) { // Allow Shift+Enter for textarea
            e.preventDefault();
            if (!$(this).is('textarea')) {
                $("#saveJobBtn").click();
            }
        }
    });

    $("#editJobForm input, #editJobForm textarea, #editJobForm select").on('keypress', function(e) {
        if (e.which === 13 && !e.shiftKey) { // Allow Shift+Enter for textarea
            e.preventDefault();
            if (!$(this).is('textarea')) {
                $("#updateJobBtn").click();
            }
        }
    });

    // Debug function to log current state
    function debugState() {
        console.log("🐛 Current State:", {
            currentPage,
            pageSize,
            sortBy,
            sortDir,
            currentSearchKeyword,
            isSearchMode,
            API_BASE_URL
        });
    }

    // Expose debug function globally for testing
    window.debugJobApp = debugState;

    console.log("✅ Job Management App initialized successfully");
    console.log("📋 Backend field mapping: job_title, job_description (underscore format)");
    console.log("🔗 API Base URL:", API_BASE_URL);
});
