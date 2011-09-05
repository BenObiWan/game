package game.network;

import game.network.config.RegistrationType;
import game.network.config.WrongAuthenticationException;

import java.util.Random;

import org.apache.mina.core.session.IoSession;

/**
 * A ClientAuthenticator is responsible for authenticating clients. It can work
 * in three different mode depending of its configuration.
 * 
 * @author benobiwan
 * 
 */
public final class ClientAuthenticator
{
	/**
	 * DistantPeerList object containing the list of all connected clients.
	 */
	private final ConnectionList _connectionList;

	/**
	 * The registration mode of this ClientAuthenticator.
	 */
	private final RegistrationType _registrationType;

	/**
	 * Random generator used by the ClientAuthenticator to generate random
	 * anonymous connection id.
	 */
	private final Random _rand;

	/**
	 * The id which will be attributed to the next anonymous client.
	 */
	private long _lNextId;

	/**
	 * Object used to assure the thread safety of the id attribution.
	 */
	private final Object _lockNextId = new Object();

	/**
	 * Create a new ClientAuthenticator.
	 * 
	 * @param registrationType
	 *            the RegistrationType of the server.
	 * @param distantPeerList
	 *            object containing the list of all connected clients.
	 */
	public ClientAuthenticator(final RegistrationType registrationType,
			final ConnectionList distantPeerList)
	{
		_registrationType = registrationType;
		_connectionList = distantPeerList;
		_lNextId = System.currentTimeMillis();
		_rand = new Random();
	}

	/**
	 * Get the RegistrationType of the server.
	 * 
	 * @return the RegistrationType of the server.
	 */
	public RegistrationType getRegistrationType()
	{
		return _registrationType;
	}

	/**
	 * Authenticate a new client and return the created Client object.
	 * 
	 * @param session
	 *            the IoSession describing the connection to the client.
	 * @param strName
	 *            the id of new client.
	 * @param strAuth
	 *            the authentication String of the new client if it tries to
	 *            authenticate itself.
	 * @param lConnectionId
	 *            the anonymous connection id of the client if it tries to
	 *            recover a severed anonymous connection.
	 * @return the created Client object.
	 * @throws WrongAuthenticationException
	 *             the Client object could not be created.
	 */
	public synchronized DistantGameClient authenticateClient(
			final IoSession session, final String strName,
			final String strAuth, final Long lConnectionId)
			throws WrongAuthenticationException
	{
		DistantGameClient cli;
		if (strAuth == null)
		{
			// Unregistered client.
			if (_registrationType == RegistrationType.MANDATORY)
			{
				throw new WrongAuthenticationException(
						"This server requires registration.");
			}
			// Trying to recover a connection
			if (lConnectionId.longValue() != 0)
			{
				cli = _connectionList.getUnregisteredClient(lConnectionId);
				if (cli != null)
				{
					if (!cli.getName().equals(strName))
					{
						throw new WrongAuthenticationException(
								"Name not matching.");
					}
					cli.closeAndChangeSession(session);
					return cli;
				}
				// if there is no connection with this id, continue as normal
			}
			if (checkForDuplicate(strName))
			{
				throw new WrongAuthenticationException("Name not available.");
			}
			final Long newId = generateNextConnectionId();
			cli = new DistantGameClient(session, strName, newId);
		}
		else
		{
			// Registered client.
			if (_registrationType == RegistrationType.NONE)
			{
				throw new WrongAuthenticationException(
						"This server doesn't support registration.");
			}
			if (!checkAuthentication(strName, strAuth))
			{
				throw new WrongAuthenticationException("Authentication failed.");
			}
			// Check if user already connected
			cli = _connectionList.getUnregisteredClient(lConnectionId);
			if (cli != null)
			{
				cli.closeAndChangeSession(session);
				return cli;
			}
			cli = new DistantGameClient(session, strName);
		}
		_connectionList.addClient(cli);
		return cli;
	}

	/**
	 * Function to call when an unregistered client want to authenticate itself
	 * to check whether the name is available. Could also be used in the
	 * registration process.
	 * 
	 * @param strName
	 *            the name to check.
	 * @return true if the name is available.
	 */
	private boolean checkForDuplicate(final String strName)
	{
		// TODO check local user name?
		switch (_registrationType)
		{
		case NONE:
			// no registration so check only in current client list.
			return _connectionList.isClientConnected(strName);
		case OPTIONAL:
		case MANDATORY:
		default:
			// check in current unregistered client list and in registered list.
			if (_connectionList.isClientConnected(strName))
			{
				return true;
			}
			return checkForRegisteredDuplicate(strName);
		}
	}

	/**
	 * Check the registered database to see if this name is registered.
	 * 
	 * @param strId
	 *            the name to check.
	 * @return true if the name is registered.
	 */
	private boolean checkForRegisteredDuplicate(
			@SuppressWarnings("unused") final String strId)
	{
		// TODO à completer quand on pourra s'enregistrer.
		return false;
	}

	/**
	 * Check whether the given authentication information are correct.
	 * 
	 * @param strName
	 *            The name used to authenticate.
	 * @param strAuth
	 *            The authentication information.
	 * @return true if the authentication information are correct.
	 */
	private boolean checkAuthentication(
			@SuppressWarnings("unused") final String strName,
			@SuppressWarnings("unused") final String strAuth)
	{
		// TODO à completer quand on pourra s'enregistrer.
		return false;
	}

	/**
	 * Register the client using it's id and it's authentication information.
	 * 
	 * @param session
	 *            the IoSession holding the connection with the client to
	 *            register.
	 * @param strName
	 *            the name requested by the client.
	 * @param strAuth
	 *            the authentication information provided by the client.
	 * @return the newly registered DistantGameClient.
	 * @throws WrongAuthenticationException
	 *             an error occured during the registration of this client.
	 */
	@SuppressWarnings("unused")
	public synchronized DistantGameClient registerClient(
			final IoSession session, final String strName, final String strAuth)
			throws WrongAuthenticationException
	{
		// TODO à completer quand on pourra s'enregistrer.
		return new DistantGameClient(session, strName);
	}

	/**
	 * Generate the id for the next anonymous connection.
	 * 
	 * @return the id for the next anonymous connection.
	 */
	private Long generateNextConnectionId()
	{
		synchronized (_lockNextId)
		{
			_lNextId += _rand.nextInt(1000);
			return Long.valueOf(_lNextId);
		}
	}
}
