pragma solidity 0.5.7;

contract Greeter {

    string greeting;

    constructor() public {
        greeting = "Hello World";
    }


    function store(string memory s) public {
        greeting = s;
    }

    function greet() public view returns (string memory) {
        return greeting;
    }
}