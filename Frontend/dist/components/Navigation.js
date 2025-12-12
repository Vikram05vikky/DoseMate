"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var react_1 = __importDefault(require("react"));
var AppContext_1 = require("../context/AppContext");
var lucide_react_1 = require("lucide-react");
var Navigation = function (_a) {
    var activeTab = _a.activeTab, onTabChange = _a.onTabChange;
    var user = (0, AppContext_1.useApp)().user;
    var adminTabs = [
        { id: 'dashboard', label: 'Dashboard', icon: lucide_react_1.LayoutDashboard },
        { id: 'doctors', label: 'Manage Doctors', icon: lucide_react_1.UserCheck },
        { id: 'patients', label: 'Patient Overview', icon: lucide_react_1.Users },
        { id: 'prescriptions', label: 'View Prescriptions', icon: lucide_react_1.FileText }
    ];
    var doctorTabs = [
        { id: 'dashboard', label: 'Dashboard', icon: lucide_react_1.LayoutDashboard },
        { id: 'add-patient', label: 'Add Patient', icon: lucide_react_1.UserPlus },
        { id: 'patient-list', label: 'My Patients', icon: lucide_react_1.List }
    ];
    var tabs = (user === null || user === void 0 ? void 0 : user.role) === 'admin' ? adminTabs : doctorTabs;
    return (<div className="bg-white rounded-xl shadow-sm border border-gray-200 mb-8">
      <div className="flex items-center justify-between p-4 border-b border-gray-200">
        <div className="flex items-center space-x-3">
          {(user === null || user === void 0 ? void 0 : user.role) === 'admin' ? (<lucide_react_1.Shield className="h-5 w-5 text-blue-600"/>) : (<lucide_react_1.Stethoscope className="h-5 w-5 text-green-600"/>)}
          <h3 className="text-lg font-semibold text-gray-900">
            {(user === null || user === void 0 ? void 0 : user.role) === 'admin' ? 'Admin Panel' : 'Doctor Portal'}
          </h3>
        </div>
      </div>
      
      <nav className="p-2">
        <div className="grid grid-cols-2 md:grid-cols-4 gap-2">
          {tabs.map(function (tab) {
            var Icon = tab.icon;
            return (<button key={tab.id} onClick={function () { return onTabChange(tab.id); }} className={"flex items-center space-x-2 px-4 py-3 rounded-lg text-sm font-medium transition-colors ".concat(activeTab === tab.id
                    ? 'bg-blue-100 text-blue-700 border border-blue-200'
                    : 'text-gray-600 hover:text-gray-900 hover:bg-gray-50')}>
                <Icon className="h-4 w-4"/>
                <span>{tab.label}</span>
              </button>);
        })}
        </div>
      </nav>
    </div>);
};
exports.default = Navigation;
