import { Routes as Switch, Route, Routes } from "react-router-dom";
import { BrowserRouter as Router } from "react-router-dom";
import Layout from "./components/Layout";

import Home from "./pages/Home";
import { ToastContextProvider } from "./context/ToastContext";



function App() {
  return (
    <Router>
      <div style={{ minHeight: "90vh", margin: "0px", padding: "0px" }}>
        <ToastContextProvider>
          <Layout>
            <Switch>
              <Route path="/" element={
                  <Home />
              } />

            </Switch>
          </Layout>
        </ToastContextProvider>
      </div>
    </Router>
  );
}

export default App;
