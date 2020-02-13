import React from 'react';
import { View, TextInput, StyleSheet } from 'react-native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { colors } from '../constants';

const InputSearch = ({ value, placeholder, onChange, onSubmit }) => {
    function onChangeText(text) {
        if (onChange) {
            onChange(text);
        }
    }

    function onSubmitEditing(event) {
        if (onSubmit) {
            onSubmit(event.nativeEvent.text);
        }
    }

    return (
        <View style={styles.container}>
            <FontAwesomeIcon
                icon={faSearch}
                size={18}
                color={colors.gray}
                style={styles.icon}
            />
            <TextInput
                style={styles.input}
                onChangeText={text => onChangeText(text)}
                onSubmitEditing={onSubmitEditing}
                value={value}
                placeholder={placeholder}
                autoCompleteType={'off'}
                autoCapitalize={'none'}
                autoCorrect={false}
                returnKeyType={'done'}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        backgroundColor: colors.white,
        borderRadius: 5,
    },
    icon: {
        marginHorizontal: 10,
    },
    input: {
        height: 40,
        fontFamily: 'Muli',
        flex: 1,
    },
});

export default InputSearch;
