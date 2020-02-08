import React, { useEffect, useState } from 'react';
import { SectionList, Button, StyleSheet, View } from 'react-native';
import MnText from '../components/MnText';
import { colors } from '../constants';
import AsyncStorage from '@react-native-community/async-storage';

const CityList = ({ navigation }) => {
    const [datasource, setDatasource] = useState([]);

    useEffect(() => {
        async function init() {
            try {
                let cities = await AsyncStorage.getItem('citiesList');
                cities = JSON.parse(cities);
                console.log(cities);
                const datasource = [];

                for (const c in cities) {
                    datasource.push({
                        title: c,
                        data: cities[c],
                    })
                }

                setDatasource(datasource);
            } catch (e) {
                console.log(e);
            }
        }

        init();
    }, []);

    return (
        <View style={styles.container}>
            <SectionList
                sections={datasource}
                keyExtractor={(item, index) => item + index}
                renderItem={({ item }) => (
                    <View style={styles.listItem}>
                        <MnText style={styles.listItemText}>{item.city}</MnText>
                    </View>
                )}
                renderSectionHeader={({ section: { title } }) => (
                    <View style={styles.listHeader}>
                        <MnText bold style={styles.listHeaderText}>{title}</MnText>
                    </View>
                )}
            />
        </View>
    );
};

CityList.navigationOptions = ({ navigation }) => ({
    title: 'Escolha a Cidade',
    headerLeft: () => <></>,
    headerRight: () => <Button onPress={() => {
        navigation.navigate('App');
    }} title="OK" />
});

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: colors.lightGray,
    },
    listHeader: {
        paddingHorizontal: 10,
        paddingVertical: 5,
        backgroundColor: colors.lightGray,
    },
    listHeaderText: {
        fontSize: 14,
    },
    listItem: {
        padding: 10,
        backgroundColor: colors.white,
    },
    listItemText: {
        fontSize: 16,
    }
})

export default CityList;