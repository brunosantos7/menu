import React, { useEffect } from 'react';
import { View, StyleSheet } from 'react-native';
import MnText from '../components/MnText';
import { colors } from '../constants';

const LoadingPage = ({ navigation }) => {
    useEffect(() => {
        setTimeout(() => {
            navigation.navigate('App');
        }, 2000);
    }, []);
    
    return (
        <View style={styles.container}>
            <MnText bold style={styles.loadingText}>Loading...</MnText>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: colors.primary,
    },
    loadingText: {
        fontSize: 21,
        color: colors.white,
    }
});

export default LoadingPage;
