import React from 'react';
import 'react-native-gesture-handler';
import { StatusBar } from 'react-native';
import AppRoutes from './src/AppRoutes';

const App = () => {
    return (
        <>
            <StatusBar barStyle="dark-content" />
            <AppRoutes />
        </>
    );
};

export default App;
