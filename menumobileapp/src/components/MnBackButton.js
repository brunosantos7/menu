import React from 'react';
import { TouchableOpacity, StyleSheet } from 'react-native';
import { NavigationActions, withNavigation } from 'react-navigation';
import { faChevronLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import { colors } from '../constants';

const MnBackButton = ({ navigation, style }) => {
    function back() {
        const backAction = NavigationActions.back();
        navigation.dispatch(backAction);
    }

    return (
        <TouchableOpacity onPress={back} style={[styles.container, style]}>
            <FontAwesomeIcon icon={faChevronLeft} size={20} color={colors.black} />
        </TouchableOpacity>
    );
}

const styles = StyleSheet.create({
    container: {
        marginLeft: 20
    }
})

export default withNavigation(MnBackButton);