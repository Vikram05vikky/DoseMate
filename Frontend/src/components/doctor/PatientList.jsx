import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Search, User, Phone, Mail, Calendar, Eye, Edit2, CheckCircle, Clock, Loader2, X, Pill, Stethoscope } from 'lucide-react';

const BASE_URL = 'http://localhost:8080';

const PatientList = () => {
  const [patients, setPatients] = useState([]);
  const [prescriptions, setPrescriptions] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedPatient, setSelectedPatient] = useState(null);
  const [currentDoctorId, setCurrentDoctorId] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  // State for history modal
  const [showHistoryModal, setShowHistoryModal] = useState(false);
  const [historyData, setHistoryData] = useState([]);
  const [historyLoading, setHistoryLoading] = useState(false);
  const [historyError, setHistoryError] = useState('');

  // 🧠 Load the logged-in doctor
  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem('user'));
    if (storedUser && storedUser.role === 'doctor') {
      setCurrentDoctorId(storedUser.id);
    } else {
      setError('Please log in as a doctor.');
      setLoading(false);
    }
  }, []);

  // 🩺 Fetch patients and prescriptions
  useEffect(() => {
    if (!currentDoctorId) return;

    const fetchData = async () => {
      try {
        setLoading(true);
        setError('');

        // Fetch patients assigned to this doctor
        const patientResponse = await axios.get(`${BASE_URL}/api/doctor/${currentDoctorId}/patients`);
        setPatients(patientResponse.data);

        // Fetch all prescriptions (MedicineReminders and OtherReminders)
        // Assuming /api/patient/all returns a combined list or similar structure for reminders
        // This is necessary for the status and history views
        const prescriptionResponse = await axios.get(`${BASE_URL}/api/patient/all`);
        setPrescriptions(prescriptionResponse.data);

      } catch (err) {
        console.error('Error fetching data:', err);
        setError('Failed to fetch patient data. Please ensure the backend is running on port 8080.');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [currentDoctorId]);

  // 🔍 Search filter
  const filteredPatients = patients.filter((patient) =>
    patient.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    patient.email?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  // 💊 Helper functions
  const getPatientStatus = (patientId) => {
    const patientPrescriptions = prescriptions.filter((p) => p.patientId === patientId);
    const hasActive = patientPrescriptions.some((p) => p.status === 'active');
    return hasActive ? 'active' : 'completed';
  };

  const getPatientPrescriptionCount = (patientId) => {
    // Counts both medicine and other treatment reminders
    return prescriptions.filter((p) => p.patientId === patientId).length;
  };
  
  // Function to fetch patient history and open modal
  const fetchPatientHistory = async (patient) => {
    if (!patient.phoneNo) {
      setHistoryError('Patient phone number is missing. Cannot fetch history.');
      setHistoryData([]);
      setSelectedPatient(patient);
      setShowHistoryModal(true);
      return;
    }

    setHistoryLoading(true);
    setHistoryError('');
    setHistoryData([]);
    setSelectedPatient(patient);
    setShowHistoryModal(true);

    try {
      const historyResponse = await axios.get(`${BASE_URL}/api/patient/history/${patient.phoneNo}`);
      // Sort history data by date/ID descending to show latest first
      const sortedHistory = historyResponse.data.sort((a, b) => (b.historyId || 0) - (a.historyId || 0));
      setHistoryData(sortedHistory);
    } catch (err) {
      console.error('Error fetching patient history:', err);
      if (err.response && err.response.status === 404) {
        setHistoryError('No generic text history found for this patient.');
      } else {
        setHistoryError('Failed to fetch patient history. Check console for details.');
      }
      setHistoryData([]);
    } finally {
      setHistoryLoading(false);
    }
  };

  // History Modal Component (Inline)
  const HistoryModal = ({ patient, history, loading, error, prescriptions, onClose }) => {
    if (!showHistoryModal || !patient) return null;

    // Filter the global prescription state to get only this patient's prescriptions
    const patientPrescriptions = prescriptions.filter(p => p.patientId === patient.id);
    
    return (
      <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full z-50 flex items-center justify-center p-4">
        <div className="relative bg-white rounded-xl shadow-2xl p-6 w-full md:w-2/3 lg:w-3/4 max-h-[90vh] overflow-y-auto transform transition-all duration-300 scale-100">
          
          {/* Modal Header */}
          <div className="flex justify-between items-center pb-4 border-b border-gray-200 sticky top-0 bg-white z-10">
            <h3 className="text-2xl font-bold text-gray-900">
              <span className="text-blue-600 mr-2">Patient Records:</span> {patient.name}
            </h3>
            <button
              onClick={onClose}
              className="text-gray-400 hover:text-gray-600 transition-colors"
              aria-label="Close"
            >
              <X className="h-6 w-6" />
            </button>
          </div>

          <div className="mt-4 space-y-8">
            {/* 1. Generic History Entries */}
            <div className="bg-gray-50 p-4 rounded-xl">
                <h4 className="text-xl font-semibold mb-4 border-b pb-2 text-gray-700 flex items-center">
                    <Stethoscope className="h-5 w-5 mr-2 text-blue-600"/> Consultation Notes
                </h4>
                {loading || historyLoading ? (
                    <div className="text-center text-gray-500 py-4"><Loader2 className="animate-spin h-5 w-5 inline mr-2" /> Loading generic history...</div>
                ) : error || historyError ? (
                    <p className="bg-red-100 p-3 rounded text-red-700">{error || historyError}</p>
                ) : history.length === 0 ? (
                    <p className="text-gray-500">No general consultation notes found.</p>
                ) : (
                    <div className="space-y-4">
                        {history.map((entry, index) => (
                            <div key={index} className="border-l-4 border-blue-400 pl-4 bg-white p-4 shadow-md rounded-lg">
                                <p className="text-xs font-medium text-gray-500 mb-1">
                                    {entry.patientHistory.split('\n')[0].includes('Initial Visit') ? 'Initial Registration' : `Visit Entry ${history.length - index}`}
                                </p>
                                <p className="whitespace-pre-wrap text-sm text-gray-800 leading-relaxed">
                                    {entry.patientHistory}
                                </p>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            {/* 2. Prescriptions (Medicines & Treatments) */}
            <div className="bg-white p-4 rounded-xl border border-gray-200 shadow-md">
                <h4 className="text-xl font-semibold mb-4 border-b pb-2 text-gray-700 flex items-center">
                    <Pill className="h-5 w-5 mr-2 text-green-600"/> Medication & Treatment History
                </h4>
                {patientPrescriptions.length === 0 ? (
                    <p className="text-gray-500">No prescriptions recorded for this patient.</p>
                ) : (
                    <div className="overflow-x-auto">
                        <table className="min-w-full divide-y divide-gray-200 rounded-lg overflow-hidden">
                            <thead className="bg-blue-50">
                                <tr>
                                    <th className="px-6 py-3 text-left text-xs font-medium text-blue-800 uppercase tracking-wider">Type / Name</th>
                                    <th className="px-6 py-3 text-left text-xs font-medium text-blue-800 uppercase tracking-wider">Time/Session</th>
                                    <th className="px-6 py-3 text-left text-xs font-medium text-blue-800 uppercase tracking-wider">Dosage / Method</th>
                                    <th className="px-6 py-3 text-left text-xs font-medium text-blue-800 uppercase tracking-wider">Total Days/Qty</th>
                                    <th className="px-6 py-3 text-left text-xs font-medium text-blue-800 uppercase tracking-wider">Start/Reminder Date</th>
                                    <th className="px-6 py-3 text-left text-xs font-medium text-blue-800 uppercase tracking-wider">Status</th>
                                </tr>
                            </thead>
                            <tbody className="bg-white divide-y divide-gray-100">
                                {patientPrescriptions.map((p, pIndex) => (
                                    <tr key={pIndex} className={p.status === 'active' ? 'bg-green-50/50 hover:bg-green-100' : 'hover:bg-gray-50'}>
                                        <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                                            <span className={`px-2 py-0.5 rounded-full text-xs mr-2 ${p.medicineName ? 'bg-indigo-100 text-indigo-800' : 'bg-pink-100 text-pink-800'}`}>
                                                {p.medicineName ? 'Medicine' : 'Treatment'}
                                            </span>
                                            {p.medicineName || p.treatmentName}
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-700">{p.session}</td>
                                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-700">{p.quantityPerSession || p.takingmethod || 'N/A'}</td>
                                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                            {p.totalQuantity ? `${p.totalQuantity} units` : 'N/A'}
                                            {p.startDate && p.lastDate && (
                                                <span className="block text-xs text-gray-500">
                                                    ({(new Date(p.lastDate).getTime() - new Date(p.startDate).getTime()) / (1000 * 60 * 60 * 24) + 1} days)
                                                </span>
                                            )}
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                            {p.startDate ? new Date(p.startDate).toLocaleDateString() : new Date(p.dateOfReminder).toLocaleDateString()}
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <span className={`px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full ${p.status === 'active' ? 'bg-green-200 text-green-800' : 'bg-gray-200 text-gray-800'}`}>
                                                {p.status || 'N/A'}
                                            </span>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
          </div>
        </div>
      </div>
    );
  };

  // ⏳ Loading state
  if (loading) {
    return (
      <div className="flex flex-col items-center justify-center py-16">
        <Loader2 className="animate-spin h-10 w-10 text-blue-600 mb-3" />
        <p className="text-gray-600">Loading patients...</p>
      </div>
    );
  }

  // ❌ Error state
  if (error) {
    return (
      <div className="text-center py-16">
        <div className="bg-red-100 rounded-full p-3 w-16 h-16 mx-auto mb-4">
          <User className="h-10 w-10 text-red-400 mx-auto" />
        </div>
        <h3 className="text-lg font-medium text-gray-900 mb-2">Error</h3>
        <p className="text-gray-600">{error}</p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Header, Search bar, and Patient cards remain the same */}
      <div className="flex items-center justify-between">
        <h2 className="text-3xl font-bold text-gray-900">My Patients</h2>
        <div className="text-sm text-gray-600">Total: {patients.length} patients</div>
      </div>

      <div className="relative">
        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400" />
        <input
          type="text"
          placeholder="Search patients by name or email..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {filteredPatients.map((patient) => {
          const status = getPatientStatus(patient.id);
          const prescriptionCount = getPatientPrescriptionCount(patient.id);

          return (
            <div key={patient.id} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow">
              <div className="flex items-center justify-between mb-4">
                <div className="flex items-center space-x-3">
                  <div className="bg-blue-100 p-2 rounded-lg">
                    <User className="h-5 w-5 text-blue-600" />
                  </div>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900">{patient.name}</h3>
                    <p className="text-sm text-gray-600">{patient.age || 'N/A'} years, {patient.gender || 'N/A'}</p>
                  </div>
                </div>

                <div className="flex items-center space-x-2">
                  <span className={`px-3 py-1 rounded-full text-xs font-medium ${
                    status === 'active'
                      ? 'bg-green-100 text-green-800'
                      : 'bg-gray-100 text-gray-800'
                  }`}>
                    {status === 'active' ? (
                      <div className="flex items-center">
                        <Clock className="h-3 w-3 mr-1" />
                        Active Treatment
                      </div>
                    ) : (
                      <div className="flex items-center">
                        <CheckCircle className="h-3 w-3 mr-1" />
                        Completed
                      </div>
                    )}
                  </span>
                </div>
              </div>

              {/* Patient details */}
              <div className="grid grid-cols-1 gap-3 mb-4">
                <div className="flex items-center space-x-2 text-gray-600">
                  <Phone className="h-4 w-4" />
                  <span className="text-sm">{patient.phoneNo || 'Not available'}</span>
                </div>
                <div className="flex items-center space-x-2 text-gray-600">
                  <Mail className="h-4 w-4" />
                  <span className="text-sm">{patient.email}</span>
                </div>
                <div className="flex items-center space-x-2 text-gray-600">
                  <Calendar className="h-4 w-4" />
                  {/* <span className="text-sm">
                    Registered: {patient.registrationDate 
                      ? new Date(patient.registrationDate).toLocaleDateString() 
                      : patient.createdAt 
                    }
                  </span> */}
                  <span className="text-sm">
  Registered: {patient.registeredDate
    ? new Date(patient.registeredDate).toLocaleDateString()
    : 'N/A'}
</span>

                </div>
              </div>

              {/* Footer */}
              <div className="border-t border-gray-200 pt-4 flex items-center justify-between">
                <div>
                  <span className="text-sm text-gray-600">Total Prescriptions</span>
                  <p className="text-lg font-semibold text-blue-600">{prescriptionCount}</p>
                </div>
                <div className="flex space-x-2">
                  <button
                    onClick={() => fetchPatientHistory(patient)}
                    className="flex items-center space-x-1 text-blue-600 hover:text-blue-700 transition-colors"
                  >
                    <Eye className="h-4 w-4" />
                    <span className="text-sm">View History</span>
                  </button>
                  <button className="flex items-center space-x-1 text-gray-600 hover:text-gray-700 transition-colors">
                    <Edit2 className="h-4 w-4" />
                    <span className="text-sm">Edit</span>
                  </button>
                </div>
              </div>
            </div>
          );
        })}
      </div>

      {filteredPatients.length === 0 && (
        <div className="text-center py-12">
          <div className="bg-gray-100 rounded-full p-3 w-16 h-16 mx-auto mb-4">
            <User className="h-10 w-10 text-gray-400 mx-auto" />
          </div>
          <h3 className="text-lg font-medium text-gray-900 mb-2">No patients found</h3>
          <p className="text-gray-600">
            {searchTerm ? 'Try adjusting your search terms.' : "You haven't added any patients yet."}
          </p>
        </div>
      )}

      {/* Render the History Modal */}
      {selectedPatient && (
        <HistoryModal 
          patient={selectedPatient} 
          history={historyData} 
          loading={historyLoading}
          error={historyError}
          prescriptions={prescriptions}
          onClose={() => setShowHistoryModal(false)} 
        />
      )}
    </div>
  );
};

export default PatientList;